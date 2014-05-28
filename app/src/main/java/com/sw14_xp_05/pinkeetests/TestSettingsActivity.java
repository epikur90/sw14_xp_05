package com.sw14_xp_05.pinkeetests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageButton;

import com.robotium.solo.Solo;
import com.sw14_xp_05.pinkee.BackgroundActivity;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.MessageList;
import com.sw14_xp_05.pinkee.SettingsActivity;

public class TestSettingsActivity extends ActivityInstrumentationTestCase2<SettingsActivity> {
    private Solo solo;
    public TestSettingsActivity() {
        super(SettingsActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testButtonBackground() {

        for( int i = 0; i < 10; i++)
        {
            solo.clickOnButton("background");
            solo.clickOnButton(i);
        }


    }

    public void testButtonTheme() {

        for( int i = 0; i < 4; i++)
        {
            solo.clickOnButton("theme");
            solo.clickOnButton(i);
        }
    }

    public void testButtonInfo() {
        solo.clickOnButton("info");
        solo.goBack();
    }

    public void testButtonShare() {
        solo.clickOnButton("share");
        solo.goBack();
    }

    public void testButtonSavefiles() {
        solo.clickOnButton("save files");
    }

    public void testButtonSaveChat() {
        solo.clickOnButton("save chat");
        solo.goBack();
    }

}