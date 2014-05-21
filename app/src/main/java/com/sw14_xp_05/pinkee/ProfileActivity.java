package com.sw14_xp_05.pinkee;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;
import android.util.Log;
import android.view.View.OnFocusChangeListener;


public class ProfileActivity extends ActionBarActivity {

    private Contact contact;

    EditText forename;
    EditText name;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ///TODO: get contact from server, with id, which must be given from click on contact list item
        contact = new Contact("Michael", "Scheucher", "michischeucher@gmail.com", "bildresource");


        //TextView forename_view = (TextView) findViewById(R.id.forenameView);
        //forename_view.setText(contact.getForename());

        forename = (EditText) findViewById(R.id.editForename);
        forename.setText(contact.getForename());
        name = (EditText) findViewById(R.id.editName);
        name.setText(contact.getName());
        email = (TextView) findViewById(R.id.emailView);
        email.setText(contact.getEmail());

    }



    public void onPause() {
        super.onPause();
        Log.i("ProfileActivity", "onPause");
        contact.setForename(forename.getText().toString());
        contact.setName(name.getText().toString());
        contact.setEmail(email.getText().toString());

        ///TODO: save contact to database
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
