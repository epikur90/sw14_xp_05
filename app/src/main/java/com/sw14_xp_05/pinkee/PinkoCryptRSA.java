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
    private PublicKey public_key;
    private PrivateKey private_key;

    public static final String PREFERENCE_NAME = "Security";


    public PinkoCryptRSA() throws InvalidKeySpecException, NoSuchAlgorithmException {
        generateRSAKey();
    }

    public PinkoCryptRSA(PublicKey pub_key){
        public_key = pub_key;
        private_key = null;
    }

    public PinkoCryptRSA(PrivateKey priv_key){
        public_key = null;
        private_key = priv_key;
    }

    private void generateRSAKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        rsa_key = keyPairGenerator.generateKeyPair();
        public_key = rsa_key.getPublic();
        private_key = rsa_key.getPrivate();

    }

    public String encryptData(String data) throws IOException {
        if(public_key == null){
            return "ERROR: NO PUBLIC KEY";
        }
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, public_key);
            encryptedData = cipher.doFinal(dataToEncrypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //   Log.d("encryptData", "end of function");

        return byteArrayToString(encryptedData);
    }

    public String decryptData(String input_string) throws IOException {
        if(private_key == null){
            return "ERROR: NO PRIVATE KEY";
        }
        byte[] data = StringToByteArray(input_string);
        byte[] decryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, private_key);
            decryptedData = cipher.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
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