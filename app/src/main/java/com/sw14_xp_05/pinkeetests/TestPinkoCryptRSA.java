package com.sw14_xp_05.pinkeetests;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.gms.internal.de;
import com.sw14_xp_05.gcm.GcmBroadcastReceiver;
import com.sw14_xp_05.gcm.GcmUtil;
import com.sw14_xp_05.gcm.ServerUtilities;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.PinKeeKee;
import com.sw14_xp_05.pinkee.PinkoCrypt;
import com.sw14_xp_05.pinkee.PinkoCryptRSA;


import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestRunner;
import android.util.Log;

import junit.framework.TestCase;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;


public class TestPinkoCryptRSA extends TestCase {

    public TestPinkoCryptRSA(String name) {
        super(name);
    }

    private String encrypt_me;
    PinkoCryptRSA crypter;

    Context mBase;

    protected void setUp() throws Exception {
        super.setUp();
        encrypt_me = "Hello World";
        crypter = new PinkoCryptRSA();
    }

    protected void tearDown()
            throws Exception {
        super.tearDown();
    }


    public void testGenerateKeys() throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyPair rsa_keys = crypter.getRsa_key();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(rsa_keys.getPublic(), RSAPublicKeySpec.class);
        RSAPrivateKeySpec rsaPrivKeySpec = keyFactory.getKeySpec(rsa_keys.getPrivate(), RSAPrivateKeySpec.class);

        Log.d("CryptoRSA", "PublicModulus: " + rsaPubKeySpec.getModulus().toString());
        Log.d("CryptoRSA", "PublicExponent: " + rsaPubKeySpec.getPublicExponent().toString());
        Log.d("CryptoRSA", "PrivateModulus: " + rsaPrivKeySpec.getModulus().toString());
        Log.d("CryptoRSA", "PrivateExponent: " + rsaPrivKeySpec.getPrivateExponent().toString());

        if (rsaPubKeySpec.getModulus().toString().isEmpty()){
            fail("No Public Modulus");
        }
        if (rsaPubKeySpec.getPublicExponent().toString().isEmpty()){
            fail("No Public PublicExponent");
        }
        if (rsaPrivKeySpec.getModulus().toString().isEmpty()){
            fail("No Private Modulus");
        }
        if (rsaPrivKeySpec.getPrivateExponent().toString().isEmpty()){
            fail("No Private PrivateExponent");
        }
    }


    public void testEncryptDecrypt() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String encrypted = crypter.encryptData(encrypt_me);
        String decrypted = "";
        decrypted = crypter.decryptData(encrypted);

        if (!encrypt_me.equals(decrypted)){
            fail("Encryption does not work properly.");
        }
    }

    public void testSending() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String encrypted = crypter.encryptData(encrypt_me);
        String decrypted = "";

        ServerUtilities.send(encrypted, "michischeucher@gmail.com");

        decrypted = crypter.decryptData(encrypted);

        if (!encrypt_me.equals(decrypted)){
            fail("Encryption does not work properly.");
        }
    }



}

