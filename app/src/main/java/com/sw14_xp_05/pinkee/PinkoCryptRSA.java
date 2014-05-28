package com.sw14_xp_05.pinkee;

import android.util.Log;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class PinkoCryptRSA {

    public KeyPair getRsa_key() {
        return rsa_key;
    }

    private KeyPair rsa_key;

    public PinkoCryptRSA(){

    }

    public void generateRSAKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        rsa_key = keyPairGenerator.generateKeyPair();

    }

    public String encryptData(String data) throws IOException {
      //  Log.d("encryptData", "start of function");

        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try {
            PublicKey pubKey = rsa_key.getPublic();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encryptedData = cipher.doFinal(dataToEncrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
     //   Log.d("encryptData", "end of function");

        return byteArrayToString(encryptedData);
    }

    public String decryptData(String input_string) throws IOException {
      //  Log.d("decryptData", "start of function");
        byte[] data = StringToByteArray(input_string);
        byte[] decryptedData = null;

        try {
            PrivateKey privateKey = rsa_key.getPrivate();
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            decryptedData = cipher.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }

      //  Log.d("decryptData", "end of function");
        return byteArrayToString(decryptedData);
    }


    public String byteArrayToString(byte[] array) {
        char[] char_array = new char[array.length];
        char c;
        String s = "";
        for (Byte b : array) {
            c = (char)b.intValue();
            s += c;
        }
        return s;
    }

    public byte[] StringToByteArray(String input_string) {
        char[] char_array = input_string.toCharArray();
        byte[] barray = new byte[input_string.length()];
        for (int i = 0 ; i < input_string.length() ; ++i) {
            barray[i] = (byte)char_array[i];
        }
        return barray;
    }

}
