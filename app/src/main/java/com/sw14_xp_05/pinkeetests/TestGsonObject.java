package com.sw14_xp_05.pinkeetests;

import android.util.Log;

import com.google.gson.Gson;
import com.sw14_xp_05.pinkee.PinKeeKee;
import com.sw14_xp_05.pinkee.PinkoCrypt;
import com.sw14_xp_05.pinkee.PinkoCryptRSA;
import com.sw14_xp_05.pinkee.Contact;

import junit.framework.TestCase;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;


public class TestGsonObject extends TestCase {

    public TestGsonObject(String name) { super(name); }

    private Contact contact;

    protected void setUp() throws Exception {
        super.setUp();

    }

    public void testGsonCompatibility() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        //create rsa keys
        PinkoCryptRSA crypter = new PinkoCryptRSA();
        KeyPair key_pair = crypter.getRsa_key();

        PinKeeKee pkk = new PinKeeKee(key_pair.getPublic());
        Gson gson = new Gson();
        String pkk_json = gson.toJson(pkk);

        //send it to server

        Log.d("pkk_json = ", pkk_json);
        PinKeeKee pkk_converted = gson.fromJson(pkk_json, PinKeeKee.class);
        if (!(pkk.getExponent().equals(pkk_converted.getExponent()) &&
                pkk.getModulus().equals(pkk_converted.getModulus()))){
            fail("Public Keys are not equal");
        }

        PublicKey public_key = PinKeeKee.generatePublicKey(pkk_converted);
        PinkoCryptRSA encrypter = new PinkoCryptRSA(public_key);
        PinkoCryptRSA decrypter = new PinkoCryptRSA(key_pair.getPrivate());
        String encrypted = encrypter.encryptData("Hello World");
        String decrypted = decrypter.decryptData(encrypted);

        Log.d("GsonTest", "encrypted: " + encrypted);
        Log.d("GsonTest", "decrypted: " + decrypted);

        if(!("Hello World".equals(decrypted))){
            fail("Decryption Failed.");
        }

    }

}