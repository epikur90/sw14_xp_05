package com.sw14_xp_05.pinkee;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.common.AccountPicker;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.Common;
import com.sw14_xp_05.pinkee.R;


public class ListActivity extends ActionBarActivity implements OnClickListener {

	// private ListView list;
	// private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		Common utils = new Common();
		
		
		if(!utils.isLoggenIn())
		{
			Intent intent = AccountPicker.newChooseAccountIntent(null, null,
					new String[] { "com.google" }, false, null, null, null, null);
			startActivityForResult(intent, 2);		
			
			
			
			
		}
		
		// Dummy contacts
		ArrayList<String> valueList = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			valueList.add("Contact " + i);
		}
		
		ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),
				R.layout.list_item, valueList);
		
		final ListView lv = (ListView) findViewById(R.id.listView);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
								
				System.out.println( view.getClass().getName() );
				ChatActivity chatWindow = new ChatActivity();
				Intent newActivity0 = new Intent(ListActivity.this, ChatActivity.class);     
	            startActivity(newActivity0);
				
			}
			
		});

		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
