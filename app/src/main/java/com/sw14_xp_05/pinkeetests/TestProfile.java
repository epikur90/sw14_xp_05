package com.sw14_xp_05.pinkeetests;
import android.test.ActivityInstrumentationTestCase2;
import com.sw14_xp_05.pinkee.ProfileActivity;
import com.sw14_xp_05.pinkee.Contact;


public class TestProfile extends ActivityInstrumentationTestCase2<ProfileActivity> {

    public TestProfile() { super(ProfileActivity.class); }

    private Contact contact;

    protected void setUp() throws Exception {
        super.setUp();
        contact = new Contact("Michael", "Scheucher", "michischeucher@gmail.com", "picturelink");
    }

    public void testGetFullName() {
        contact.setForename("Vorname");
        contact.setName("Nachname");
        contact.setEmail("email");

        String full_name = contact.getFullName();
        assertTrue("total korrekt", full_name != "Michael Scheucher");
    }

}
