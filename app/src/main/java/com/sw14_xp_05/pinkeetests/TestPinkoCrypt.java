package com.sw14_xp_05.pinkeetests;

import android.test.ActivityInstrumentationTestCase2;
import com.sw14_xp_05.pinkee.PinkoCrypt;


import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestRunner;
import android.util.Log;

import junit.framework.TestCase;

public class TestPinkoCrypt extends TestCase {

    public TestPinkoCrypt(String name) {
        super(name);
    }

    private String encrypt_me;

    protected void setUp() throws Exception {
        super.setUp();
        encrypt_me = "Hello World";
    }

    protected void tearDown()
            throws Exception {
        super.tearDown();
    }
	public void testEncryptAndDecrypt() throws Exception {
		String encrypted_text;
        String decrypted_text;
        encrypted_text = PinkoCrypt.encrypt("masterpasswort",encrypt_me);
        Log.d("CryptoTest", encrypted_text);
        decrypted_text = PinkoCrypt.decrypt("masterpasswort",encrypted_text);
        Log.d("CryptoTest", decrypted_text);
        if (!encrypt_me.equals(decrypted_text)){
            fail("No Proper Encryption");
        }
	}
}

