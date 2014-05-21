package com.sw14_xp_05.pinkee;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;


public class ProfileActivity extends ActionBarActivity {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ///TODO: get contact from server
        contact = new Contact("Michael", "Scheucher", "michischeucher@gmail.com", "bildresource");


        TextView forename_view = (TextView) findViewById(R.id.forenameView);
        forename_view.setText(contact.getForename());

        TextView name_view = (TextView) findViewById(R.id.nameView);
        name_view.setText(contact.getName());

        TextView email_view = (TextView) findViewById(R.id.emailView);
        email_view.setText(contact.getEmail());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
