package com.sw14_xp_05.pinkee.testpackage;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.RegisterActivity;

public class TestRegister extends ActivityInstrumentationTestCase2<RegisterActivity> {
	
	private Solo mySolo;
	public TestRegister() {
		super(RegisterActivity.class);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mySolo = new Solo(getInstrumentation(), getActivity());
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testClickAllElements() {
		mySolo.clickOnButton("clear");
		mySolo.clickOnButton("send");
	}
	public void testRegister() {
		RegisterActivity myact = (RegisterActivity)mySolo.getCurrentActivity();
		if (myact.getRegid().equals("")) {
			assertTrue("Registration ID is not found or generated", false);
		} 
		
	}
	
	
}
