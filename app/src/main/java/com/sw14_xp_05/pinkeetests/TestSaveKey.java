package com.sw14_xp_05.pinkeetests;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.MessageList;
import com.sw14_xp_05.pinkee.PinKeeKee;
import com.sw14_xp_05.pinkee.PinkoCryptRSA;
import com.sw14_xp_05.pinkee.SettingsActivity;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;


public class TestSaveKey extends ActivityInstrumentationTestCase2<SettingsActivity> {
	private Solo solo;
    private PinKeeKee pkk_private;

    public TestSaveKey() {
		super(SettingsActivity.class);
	}

    PinkoCryptRSA crypter;
    Context context;
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
        crypter = new PinkoCryptRSA();
        context = getActivity().getApplicationContext();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

    public void testSavePrivateKey() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        KeyPair key_pair = crypter.getRsa_key();
        PinKeeKee private_key_original = new PinKeeKee(key_pair.getPrivate());
        PinKeeKee.saveKey(key_pair.getPrivate(), context);

        PrivateKey private_key = PinKeeKee.loadPrivateKey(context);
        PinKeeKee private_key_loaded = new PinKeeKee(private_key);

        if (private_key_original.getExponent().equals(private_key_loaded.getExponent()))
            Log.i("Krypter", "same exponents");
        else {
            Log.i("Krypter", "faiiiilll not same exponents");
            fail();
        }

        if (private_key_original.getModulus().equals(private_key_loaded.getModulus()))
            Log.i("Krypter", "same modulus");
        else {
            Log.i("Krypter", "faiiiilll not same modulus");
            fail();
        }

    }
}