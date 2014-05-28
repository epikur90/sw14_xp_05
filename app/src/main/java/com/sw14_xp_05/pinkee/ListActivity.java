package com.sw14_xp_05.pinkee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.AccountPicker;

import java.util.ArrayList;


public class ListActivity extends ActionBarActivity implements OnClickListener {

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

    private ContactListAdapter adapter;
    private ArrayList<Contact> valueList;
    private SQLiteStorageHelper dbhelper;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);

    Common utils = new Common();


    /*if (!utils.isLoggenIn()) {
      Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                                                           new String[]{"com.google"}, false, null, null, null, null);
      startActivityForResult(intent, 2);


    }*/

    dbhelper = new SQLiteStorageHelper(this.getBaseContext());

    Contact c1 = new Contact();
    Contact c2 = new Contact("Hugo","Hodor","hugoh@yourmama.com","link1");
    Contact c3 = new Contact("Heinzi","Wieauchimmer","heinzw@yourmama.com","link2");
    Contact c4 = new Contact("Eva","Kuier","evakuier@yourmama.com","link3");
    Contact c5 = new Contact("Susi","Strolch","susi@yourmama.com","link4");
    Contact c6 = new Contact("Mickey","Mouse","mmickey@yourmama.com","link5");

    dbhelper.saveContact(c1);
    dbhelper.saveContact(c2);
    dbhelper.saveContact(c3);
    dbhelper.saveContact(c4);
    dbhelper.saveContact(c5);
    dbhelper.saveContact(c6);

    valueList = dbhelper.getContacts();

    adapter = new ContactListAdapter(getApplicationContext(), valueList);

    final ListView lv = (ListView) findViewById(R.id.listView);
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view,
                              int position, long id) {

        System.out.println(view.getClass().getName());
        Intent newActivity0 = new Intent(ListActivity.this, ChatActivity.class);

        newActivity0.putExtra("contact", adapter.getItem(position));
        startActivity(newActivity0);
      }

    });

    lv.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
      if (id == R.id.action_settings) {
          Intent newActivity0 = new Intent(ListActivity.this, SettingsActivity.class);
          startActivity(newActivity0);
          return true;
      }
    if (id == R.id.action_add_contact) {

          // Set an EditText view to get user input
          final EditText emailEditText = new EditText(this);

          final AlertDialog dialog = new AlertDialog.Builder(this)
                  .setTitle(R.string.action_add_contact)
                  .setNegativeButton("Cancel", null)
                  .setPositiveButton("Ok", null)
                  .setMessage(R.string.action_add_contact_message)
                  .setView(emailEditText)
                  .create();
          dialog.setOnShowListener(new DialogInterface.OnShowListener() {

              @Override
              public void onShow(DialogInterface arg0) {

                  Button okButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                  okButton.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View arg0) {

                          if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                              Toast.makeText(getBaseContext(), R.string.action_add_contact_invalid_email, Toast.LENGTH_LONG).show();
                          }
                          else{
                              dialog.dismiss();
                              //valueList.add(emailEditText.getText().toString());
                              dbhelper.saveContact(new Contact("", "", emailEditText.getText().toString(), ""));
                              adapter.notifyDataSetChanged();
                          }

                      }
                  });
              }
          });
          dialog.show();

          //TODO


          return true;
      }
      if (id == R.id.action_show_contacts) {
          Log.i("ListActivity", "action_show_contacts clicked");
          Intent new_activity_contacts = new Intent(this, ContactsActivity.class);
          startActivity(new_activity_contacts);
          return true;
      }

    return super.onOptionsItemSelected(item);
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_main, container,
                                       false);
      return rootView;
    }
  }

  @Override
  public void onClick(View v) {


  }

    protected void onResume()
    {
        super.onResume();

        final SharedPreferences sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String Colorchange = "#FFFFFF";
        Integer Themechange = 0;

        View background = getWindow().getDecorView();

        if(sharedpreferences.contains(Mycolor))
        {
            Colorchange = sharedpreferences.getString(Mycolor, "");

            background.setBackgroundColor(Color.parseColor(Colorchange));
        }

        if(sharedpreferences.contains(Mytheme))
        {
            Themechange = sharedpreferences.getInt(Mytheme,0);

            background.setBackgroundResource(Themechange);
        }

        adapter.setSearchArrayList(dbhelper.getContacts());

        this.adapter.notifyDataSetChanged();
    }
}