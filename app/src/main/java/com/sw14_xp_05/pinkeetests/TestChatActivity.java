package com.sw14_xp_05.pinkeetests;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.ChatActivity;

public class TestChatActivity extends ActivityInstrumentationTestCase2<ChatActivity> {
	private Solo solo;
	public TestChatActivity() {
		super(ChatActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testChatTextField() {
		solo.enterText(0,"so extreme!");
		solo.getText("so extreme!");
	}

    public void testSendButton() {
        solo.enterText(0, "Hello nerd!");
        solo.clickOnButton("Send");
        assertTrue("EditText should be empty", solo.getEditText(0).getText().toString().isEmpty());
        solo.getText("Hello nerd!");
    }
}