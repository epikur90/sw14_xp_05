package com.sw14_xp_05.pinkeetests;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.Contact;
import com.sw14_xp_05.pinkee.MessageList;
import com.sw14_xp_05.pinkee.ProfileActivity;

public class TestProfileActivity extends ActivityInstrumentationTestCase2<ProfileActivity> {
	private Solo solo;
	public TestProfileActivity() {
		super(ProfileActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();

        Intent i = new Intent();
        i.putExtra("contact", new Contact());
        setActivityIntent(i);

		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

    public void testFieldsExists() {
        solo.enterText(0, "Vorname");
        solo.enterText(1, "Nachname");
    }
}