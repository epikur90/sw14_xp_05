package com.sw14_xp_05.pinkee;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sw14_xp_05.gcm.DataProvider;
import com.sw14_xp_05.gcm.GcmUtil;
import com.sw14_xp_05.gcm.ServerUtilities;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

public class ChatActivity extends ActionBarActivity {

	private Button buttonSend;
	private EditText textFieldMessage;
	private MessageList messageList;
    private static Contact activeContact;
    private Contact contact;
    private GcmUtil gcmUtil;
    private PinkoCryptRSA encrypter;

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




        this.buttonSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
                if (textFieldMessage.getText().toString().equals("")) return;

				Message message = new Message(textFieldMessage.getText().toString(), contact);

                // Send message to the server
                send(textFieldMessage.getText().toString());

				textFieldMessage.getText().clear();
				messageList.displayMessage(message);
				
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

        registerReceiver(registrationStatusReceiver, new IntentFilter(Common.ACTION_REGISTER));

        SQLiteStorageHelper.getInstance(getApplicationContext()).registerObserver(messageList);



	}


    private void send(final String txt) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                String targetEmail = contact.getEmail();
                try {
                    Log.d("Cryptooooo", "encrypted = " + encrypter.encryptData(txt));
                    ServerUtilities.send(encrypter.encryptData(txt), targetEmail);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                Log.d("ChatActivity", "Sending message to: " + targetEmail);
                ContentValues values = new ContentValues(2);
                values.put(DataProvider.COL_TYPE,  DataProvider.MessageType.OUTGOING.ordinal());
                values.put(DataProvider.COL_MESSAGE, txt);
                values.put(DataProvider.COL_RECEIVER_EMAIL, targetEmail);
                values.put(DataProvider.COL_SENDER_EMAIL, Common.getPreferredEmail());
                getContentResolver().insert(DataProvider.CONTENT_URI_MESSAGES, values);
                Log.d("ChatActivity", "Message was send: " + txt);

                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(msg)) {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(null, null, null);
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

    @Override
    protected void onResume() {
        super.onResume();

        final SharedPreferences sharedpreferences = getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
        String Colorchange = "#FFFFFF";
        Integer Themechange = 0;

        View background = getWindow().getDecorView();

        if (sharedpreferences.contains(Mycolor)) {
            Colorchange = sharedpreferences.getString(Mycolor, "");

            background.setBackgroundColor(Color.parseColor(Colorchange));
        }

        if (sharedpreferences.contains(Mytheme)) {
            Themechange = sharedpreferences.getInt(Mytheme, 0);

            background.setBackgroundResource(Themechange);
        }

        this.contact = (Contact) getIntent().getSerializableExtra("contact");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            getActionBar().setTitle(contact.toString());


        this.messageList.setContact(this.contact);
        this.messageList.initAdapter();

        activeContact = this.contact;
        SQLiteStorageHelper helper = SQLiteStorageHelper.getInstance(getBaseContext());

        String public_key_string = contact.getPublicKey();
        if (public_key_string == null) {
            //get public key from server
            try {
                public_key_string = ServerUtilities.askPublicKeyString(contact.getEmail());
                contact.setPublicKey(public_key_string);
                helper.saveContact(contact);
                PinKeeKee pkk_public = PinKeeKee.getObjectFromGson(public_key_string);
                PublicKey public_key = null;
                public_key = PinKeeKee.generatePublicKey(pkk_public);
                encrypter = new PinkoCryptRSA(public_key);
                Log.d("ChatActivity", "onResume - public_key_string = " + public_key_string);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Log.d("ChatActivity", "onResume - public key NOT found");
        } else {
            PinKeeKee pkk_public = PinKeeKee.getObjectFromGson(public_key_string);
            PublicKey public_key = null;
            try {
                public_key = PinKeeKee.generatePublicKey(pkk_public);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            encrypter = new PinkoCryptRSA(public_key);
            Log.d("ChatActivity", "onResume - public key found");

        }


    }

    @Override
    protected void onPause() {
        activeContact = null;
        super.onPause();
    }

    private BroadcastReceiver registrationStatusReceiver = new  BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && Common.ACTION_REGISTER.equals(intent.getAction())) {
                switch (intent.getIntExtra(Common.EXTRA_STATUS, 100)) {
                    case Common.STATUS_SUCCESS:
                        getSupportActionBar().setSubtitle("online");
                        buttonSend.setEnabled(true);
                        break;

                    case Common.STATUS_FAILED:
                        getSupportActionBar().setSubtitle("offline");
                        break;
                }
            }
        }
    };

    public static Contact getActiveContact(){
        return activeContact;
    }
}
