package com.sw14_xp_05.pinkee;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import java.util.Date;
import android.util.Log;

public class ChatActivity extends ActionBarActivity {

	private Button buttonSend;
	private EditText textFieldMessage;
	private MessageList messageList;
    private Contact contact;

    public static final String MyPreferences = "MyPrefs";
    public static final String Mycolor = "Mycolor";
    public static final String Mytheme = "Mytheme";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		this.buttonSend = (Button) this.findViewById(R.id.buttonSend);
		this.textFieldMessage = (EditText) this.findViewById(R.id.textFieldMessage);
		this.messageList = (MessageList) this.findViewById(R.id.messageList);

        this.contact =  (Contact) getIntent().getSerializableExtra("contact");
        Log.d("chatactivity", contact.getEmail());

        this.messageList.setContact(this.contact);
        this.messageList.initAdapter();
        Log.d("chatactivity", this.messageList.getContact().getEmail());

		this.buttonSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Message message = new Message(textFieldMessage.getText().toString(), contact);

				textFieldMessage.getText().clear();
				messageList.displayMessage(message);
				
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat, menu);
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

    public MessageList getMessageList(){
        return messageList;
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
			View rootView = inflater.inflate(R.layout.fragment_chat, container,
					false);
			return rootView;
		}
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


    }
}
