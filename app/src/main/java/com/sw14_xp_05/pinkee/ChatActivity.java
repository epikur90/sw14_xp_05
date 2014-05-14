package com.sw14_xp_05.pinkee;

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

public class ChatActivity extends ActionBarActivity {

	private Button buttonSend;
	private EditText textFieldMessage;
	private MessageList messageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		this.buttonSend = (Button) this.findViewById(R.id.buttonSend);
		this.textFieldMessage = (EditText) this.findViewById(R.id.textFieldMessage);
		this.messageList = (MessageList) this.findViewById(R.id.messageList);

        messageList.displayMessage(new Message("Hi Albi, meld dich!", "receiver@iwo.com", new Date(), new Date()));
        messageList.displayMessage(new Message("Sers, bin online...", null, new Date(), new Date()));
		
		this.buttonSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Message message = new Message(textFieldMessage.getText().toString());

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
}
