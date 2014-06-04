package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import android.util.Log;

public class PinKeeKee {

    private BigInteger modulus;
    private BigInteger exponent;

    public static final String PRIVATE_KEY_PREF = "priv_key";
    public static final String PUBLIC_KEY_PREF = "pub_key";


    public PinKeeKee(){  }

    //used as public key holder
    public PinKeeKee(PublicKey public_key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec rsaPubKeySpec = keyFactory.getKeySpec(public_key, RSAPublicKeySpec.class);
        modulus = rsaPubKeySpec.getModulus();
        exponent = rsaPubKeySpec.getPublicExponent();
    }
    static public PublicKey generatePublicKey(PinKeeKee pkk) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubkeyspec = new RSAPublicKeySpec(pkk.getModulus(),pkk.getExponent());
        return keyFactory.generatePublic(pubkeyspec);
    }

    //used as private key holder
    public PinKeeKee(PrivateKey priv_key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec rsa_priv_key_spec = keyFactory.getKeySpec(priv_key, RSAPrivateKeySpec.class);
        modulus = rsa_priv_key_spec.getModulus();
        exponent = rsa_priv_key_spec.getPrivateExponent();
    }
    static public PrivateKey generatePrivateKey(PinKeeKee pkk_private) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Log.i("PinKeeKee", "generatePrivateKey - begin");
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec priv_key_spec = new RSAPrivateKeySpec(pkk_private.getModulus(), pkk_private.getExponent());
        Log.i("PinKeeKee", "generatePrivateKey - keyFactory.generatePrivate(priv_key_spec)" + keyFactory.generatePrivate(priv_key_spec).toString());
        return keyFactory.generatePrivate(priv_key_spec);
    }

    public BigInteger getExponent() {
        return exponent;
    }
    public BigInteger getModulus() {
        return modulus;
    }

    //used for converting to strings and back from string to object
    public String getGsonObject() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
    static public PinKeeKee getObjectFromGson(String json_object) {
        Gson gson = new Gson();
        return gson.fromJson(json_object, PinKeeKee.class);
    }


    //store it
    static public void saveKey(PrivateKey private_key, Context context_) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SharedPreferences pref = context_.getSharedPreferences(PinkoCryptRSA.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        PinKeeKee pkk_private = new PinKeeKee(private_key);
        editor.putString(PinKeeKee.PRIVATE_KEY_PREF, pkk_private.getGsonObject());
        editor.commit();
    }


    //load it
    static public PrivateKey loadPrivateKey(Context context_) throws InvalidKeySpecException, NoSuchAlgorithmException {
        Log.i("PinKeeKee", "loadPrivateKey");
        SharedPreferences pref = context_.getSharedPreferences(PinkoCryptRSA.PREFERENCE_NAME, Context.MODE_PRIVATE);
        Log.i("PinKeeKee", "loadPrivateKey - getSharedPreferences");
        String private_json = pref.getString(PinKeeKee.PRIVATE_KEY_PREF, null);
        Log.i("PinKeeKee", "loadPrivateKey - getString");
        PinKeeKee pkk_private = PinKeeKee.getObjectFromGson(private_json);
        Log.i("PinkeeKee", "private_json = " + private_json);
        Log.i("PinkeeKee", "pkk_private.exponent = " + pkk_private.getExponent());
        return generatePrivateKey(pkk_private);
    }


}


