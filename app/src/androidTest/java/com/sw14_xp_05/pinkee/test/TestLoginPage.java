package com.sw14_xp_05.pinkee.test;

import com.sw14_xp_05.pinkee.LoginActivity;
import com.robotium.solo.Solo;


import android.test.ActivityInstrumentationTestCase2;

public class TestLoginPage extends ActivityInstrumentationTestCase2<LoginActivity> {
	
	private Solo mySolo;
	public TestLoginPage() {
		super(LoginActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mySolo = new Solo(getInstrumentation(), getActivity());
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testClickAllElements() {
		mySolo.clickOnCheckBox(0);
		mySolo.clickOnButton("login");
	}
	public void testInputFields() {
		mySolo.clickOnEditText(0);
		mySolo.clickOnEditText(1);
		mySolo.enterText(0, "michi@gmail.com");
		mySolo.getText("michi@gmail.com");
		mySolo.enterText(1, "123456");
		mySolo.getText("123456");
	}
}
