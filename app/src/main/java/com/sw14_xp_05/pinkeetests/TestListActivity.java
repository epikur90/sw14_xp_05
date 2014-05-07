package com.sw14_xp_05.pinkee.testpackage;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;
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
	
	public void testClickChatListElement() {
		mySolo.clickInList(0);
		mySolo.assertCurrentActivity("Chat window not open", ListActivity.class);
	}
}