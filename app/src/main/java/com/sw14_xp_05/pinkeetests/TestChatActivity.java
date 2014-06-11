package com.sw14_xp_05.pinkeetests;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.Contact;
import com.sw14_xp_05.pinkee.MessageList;

public class TestChatActivity extends ActivityInstrumentationTestCase2<ChatActivity> {
	private Solo solo;
	public TestChatActivity() {
		super(ChatActivity.class);
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
	
	public void testChatTextField() {
		//solo.enterText(0,"so extreme!");
		//solo.getText("so extreme!");
	}

    public void testEmptyMessageSend() {

        solo.enterText(0, "Test empty message");
        solo.clickOnButton("Send");
        solo.enterText(0, "Test empty message2");
        solo.clickOnButton("Send");
        MessageList list = ((ChatActivity)solo.getCurrentActivity()).getMessageList();

        solo.clearEditText(0);
        int entry_count = list.getCount();

        solo.clickOnButton("Send");

        assertTrue("Empty message should not be sent!" + entry_count + " " + list.getCount(), entry_count == list.getCount());
    }

    public void testSendButton() {
        solo.enterText(0, "Hello nerd!");
        solo.clickOnButton("Send");
        assertTrue("EditText should be empty", solo.getEditText(0).getText().toString().isEmpty());
        solo.getText("Hello nerd!");
    }
}