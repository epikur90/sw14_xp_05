package com.sw14_xp_05.gcm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.sw14_xp_05.pinkee.Common;
import com.sw14_xp_05.pinkee.PinKeeKee;
import com.sw14_xp_05.pinkee.PinkoCryptRSA;

import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

public class GcmUtil {
	
	private static final String TAG = "GcmUtil";
	
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "onServerExpirationTimeMs";
	
    /**
     * Default lifespan (7 days) of a reservation until it is considered expired.
     */
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;
    
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();    

    private Context ctx;
	private SharedPreferences prefs;
	private GoogleCloudMessaging gcm;
	private AsyncTask registrationTask;

    private Context context;

	public GcmUtil(Context applicationContext) {
        super();
        Log.d("GcmUtil", "constructor begin");
        ctx = applicationContext;
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		String regid = getRegistrationId();
        Log.d("GcmUtil", "regid=" + regid);
        if (regid.length() == 0) {
            Log.d("GcmUtil", "Registering in Background...");
            registerBackground(applicationContext);
        } else {
            Log.d("GcmUtil", "Already registered");
        	broadcastStatus(true);
        }
		gcm = GoogleCloudMessaging.getInstance(ctx);		
	}
	
	/**
	 * Gets the current registration id for application on GCM service.
	 * <p>
	 * If result is empty, the registration has failed.
	 *
	 * @return registration id, or empty string if the registration is not
	 *         complete.
	 */
	private String getRegistrationId() {
	    String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	    if (registrationId.length() == 0) {
	        return "";
	    }

	    int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion();
	    if (registeredVersion != currentVersion || isRegistrationExpired()) {

	        return "";
	    }
	    return registrationId;
	}
	
	/**
	 * Stores the registration id, app versionCode, and expiration time in the
	 * application's {@code SharedPreferences}.
	 *
	 * @param regId registration id
	 */
	private void setRegistrationId(String regId) {
	    int appVersion = getAppVersion();
	    SharedPreferences.Editor editor = prefs.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

	    editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
	    editor.commit();
	}	
	
	/**
	 * @return Application's version code from the {@code PackageManager}.
	 */
	private int getAppVersion() {
	    try {
	        PackageInfo packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	        // should never happen
	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	
	/**
	 * Checks if the registration has expired.
	 *
	 * <p>To avoid the scenario where the device sends the registration to the
	 * server but the server loses it, the app developer may choose to re-register
	 * after REGISTRATION_EXPIRY_TIME_MS.
	 *
	 * @return true if the registration has expired.
	 */
	private boolean isRegistrationExpired() {
	    long expirationTime = prefs.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
	    return System.currentTimeMillis() > expirationTime;
	}	
	
	/**
	 * Registers the application with GCM servers asynchronously.
	 * <p>
	 * Stores the registration id, app versionCode, and expiration time in the 
	 * application's shared preferences.
	 */
	private void registerBackground(final Context applicationContext) {
		registrationTask = new AsyncTask<Void, Void, Boolean>() {
	        @Override
	        protected Boolean doInBackground(Void... params) {
	            long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
	            for (int i = 1; i <= MAX_ATTEMPTS; i++) {

		            try {
		                if (gcm == null) {
		                    gcm = GoogleCloudMessaging.getInstance(ctx);
		                }
		                String regid = gcm.register(Common.getSenderId());

                        Log.d("GcmUtil", "Registration from device/user");
                        //create key
                        PinkoCryptRSA crypter = new PinkoCryptRSA();
                        KeyPair key_pair = crypter.getRsa_key();

                        //send public, regid and email to server
                        PinKeeKee pkk_public = new PinKeeKee(key_pair.getPublic());
                        String pub_key = pkk_public.getGsonObject();
                        ServerUtilities.register(Common.getPreferredEmail(), regid, pub_key);

                        //store private key
                        PinKeeKee.saveKey(key_pair.getPrivate(), applicationContext);

                        //just for debugging
                        Gson gson = new Gson();
                        PinKeeKee pkk_private = new PinKeeKee(key_pair.getPrivate());
                        Log.d("GcmUtil", "############################ NEW KEYSSSSSSSS ###################");
                        Log.d("GcmUtil", "private_key_string = " + gson.toJson(pkk_private));
                        Log.d("GcmUtil", "public_key_string = " + gson.toJson(pkk_public));


//		                ServerUtilities.register(Common.getPreferredEmail(), regid, );
	
		                setRegistrationId(regid);
		                return Boolean.TRUE;
		                
		            } catch (IOException ex) {
		                if (i == MAX_ATTEMPTS) {
		                    break;
		                }
		                try {
		                    Thread.sleep(backoff);
		                } catch (InterruptedException e1) {
		                    Thread.currentThread().interrupt();
		                }
		                backoff *= 2;		                
		            } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                }
	            return Boolean.FALSE;
	        }

	        @Override
	        protected void onPostExecute(Boolean status) {
	        	broadcastStatus(status);
	        }
	    }.execute(null, null, null);
	}
	
	private void broadcastStatus(boolean status) {
    	Intent intent = new Intent(Common.ACTION_REGISTER);
        intent.putExtra(Common.EXTRA_STATUS, status ? Common.STATUS_SUCCESS : Common.STATUS_FAILED);
        ctx.sendBroadcast(intent);		
	}
	
	public void cleanup() {
		if (registrationTask != null) {
			registrationTask.cancel(true);
		}
		if (gcm != null) {
			gcm.close();
		}
	}	
	
}
