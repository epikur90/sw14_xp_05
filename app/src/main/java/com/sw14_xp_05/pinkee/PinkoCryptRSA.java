package com.sw14_xp_05.pinkee;

import android.util.Log;

import com.google.gson.Gson;

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

    public String encryptData(String data) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        Log.d("PinkoCryptRSA", "before encryption = " + data);
        //just for debugging
        PinKeeKee pkk = new PinKeeKee(public_key);
        Gson gson = new Gson();
        Log.d("PinkoCryptRSA", "public_key_string = " + gson.toJson(pkk));



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
        Log.d("PinkoCryptRSA", "encrypted data = " + byteArrayToString(encryptedData));
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
        String return_string = new String(decryptedData);
        return return_string;
    }


    public static String byteArrayToString(byte[] array) {
        char[] char_array = new char[array.length];
        char c;
        String s = "";

        int first_bitmuster = 0xF0;
        int second_bitmuster = 0x0F;

        char first_value = 0;
        char second_value = 0;

        for (byte b : array) {
            first_value = (char)(b & first_bitmuster);
            first_value = (char)(first_value >>> 4);
            first_value += 97;
            s += first_value;

            second_value = (char)(b & second_bitmuster);
            second_value += 97;
            s += second_value;
        }
        Log.d("byteArrayToString = ", s);
        return s;
    }


    public static byte[] StringToByteArray(String input_string) {
        byte[] bytes = new byte[input_string.length() / 2];

        char first_value = 0;
        char second_value = 0;

        int counter = 0;
        for (int i = 0; i < input_string.length(); i = i + 2) {
            first_value = input_string.charAt(i);
            first_value -= 97;
            first_value = (char)(first_value << 4);

            second_value = input_string.charAt(i + 1);
            second_value -= 97;

            bytes[counter++] = (byte)(first_value | second_value);

            //s += Integer.toHexString(b.intValue()) + "#";
        }
        return bytes;
    }



}