package com.sw14_xp_05.pinkee.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.ListActivity;

public class TestListActivity extends ActivityInstrumentationTestCase2<ListActivity> {
	private Solo mySolo;
	public TestListActivity() {
		super(ListActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mySolo = new Solo(getInstrumentation(), getActivity());
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testlickChatListElement() {
		mySolo.clickInList(0);
		Activity chatActivity = mySolo.getCurrentActivity();
		assertTrue("Chat window not open", chatActivity instanceof ChatActivity);
	}
}