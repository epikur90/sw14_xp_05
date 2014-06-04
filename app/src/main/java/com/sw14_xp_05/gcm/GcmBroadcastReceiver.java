package com.sw14_xp_05.gcm;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.sw14_xp_05.gcm.DataProvider.MessageType;
import com.sw14_xp_05.pinkee.ChatActivity;
import com.sw14_xp_05.pinkee.Common;
import com.sw14_xp_05.pinkee.Contact;
import com.sw14_xp_05.pinkee.Message;
import com.sw14_xp_05.pinkee.MessageList;
import com.sw14_xp_05.pinkee.R;
import com.sw14_xp_05.pinkee.SQLiteStorageHelper;

import java.util.ArrayList;
import java.util.Date;

public class GcmBroadcastReceiver extends BroadcastReceiver {
	
	private static final String TAG = "GcmBroadcastReceiver";
	private Context ctx;
    private ArrayList<MessageList> observers;

    public GcmBroadcastReceiver(){
        observers = new ArrayList<MessageList>();
    }

    public void registerObserver(MessageList observer){
        this.observers.add(observer);
    }

	@Override
	public void onReceive(Context context, Intent intent) {
		ctx = context;
		PowerManager mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		WakeLock mWakeLock = mPowerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
		mWakeLock.acquire();
        Log.d("GcmBroadcastProvider", "after aquire()");
		try {
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
			String messageType = gcm.getMessageType(intent);
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				//TODO sendNotification("Send error", false);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				//TODO sendNotification("Deleted messages on server", false);
  			} else {
				String msg = intent.getStringExtra(DataProvider.COL_MESSAGE);
                Log.d("GcmBroadcastProvider", "Message received: " + msg);
                String senderEmail = intent.getStringExtra(DataProvider.COL_SENDER_EMAIL);
				String receiverEmail = intent.getStringExtra(DataProvider.COL_RECEIVER_EMAIL);
				ContentValues values = new ContentValues(2);
				values.put(DataProvider.COL_TYPE,  MessageType.INCOMING.ordinal());				
				values.put(DataProvider.COL_MESSAGE, msg);
				values.put(DataProvider.COL_SENDER_EMAIL, senderEmail);
				values.put(DataProvider.COL_RECEIVER_EMAIL, receiverEmail);
				context.getContentResolver().insert(DataProvider.CONTENT_URI_MESSAGES, values);

                SQLiteStorageHelper helper = SQLiteStorageHelper.getInstance(ctx);
                Contact sender = helper.getContact(senderEmail);

                helper.saveMessage(new Message(msg, sender, new Date()));

                // If right chatactivity is open, put message in chat
                if(ChatActivity.getActiveContact() == null){
                    sendNotification(msg, true, sender);
                }

//				if (Common.isNotify()) {
//
//				}

			}
			setResultCode(Activity.RESULT_OK);
		} finally {
			mWakeLock.release();
		}
	}

	private void sendNotification(String text, boolean launchApp, Contact senderContact) {
		NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder notification = new NotificationCompat.Builder(ctx);
		notification.setContentTitle(ctx.getString(R.string.app_name));
		notification.setContentText(text);
		notification.setAutoCancel(true);
		notification.setSmallIcon(R.drawable.ic_launcher);
		if (!TextUtils.isEmpty(Common.getRingtone())) {
			notification.setSound(Uri.parse(Common.getRingtone()));
		}

		if (launchApp) {
			Intent intent = new Intent(ctx, ChatActivity.class);

            intent.putExtra("contact", senderContact);

			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent pi = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setContentIntent(pi);
		}
	
		mNotificationManager.notify(1, notification.build());
	}
}