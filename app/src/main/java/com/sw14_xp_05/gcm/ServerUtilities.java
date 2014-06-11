/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sw14_xp_05.gcm;

import android.os.AsyncTask;
import android.util.Log;

import com.sw14_xp_05.pinkee.Common;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

	private static final String TAG = "ServerUtilities";

	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();

	/**
	 * Register this account/device pair within the server.
	 */
	public static void register(final String email, final String regId, final String pub_key) {
        Log.d("ServerUtilities", "register " + email);
		String serverUrl = Common.getServerUrl() + "/register";
		Map<String, String> params = new HashMap<String, String>();
		params.put(DataProvider.SENDER_EMAIL, email);
		params.put(DataProvider.REG_ID, regId);
        params.put(DataProvider.PUB_KEY, pub_key);

		try {
			post(serverUrl, params, MAX_ATTEMPTS);
		} catch (IOException e) {
		}
	}

	/**
	 * Unregister this account/device pair within the server.
	 */
	public void unregister(final String email) {

		String serverUrl = Common.getServerUrl() + "/unregister";
		Map<String, String> params = new HashMap<String, String>();
		params.put(DataProvider.SENDER_EMAIL, email);
		try {
			post(serverUrl, params, MAX_ATTEMPTS);
		} catch (IOException e) {

		}

    }

    public static String askPublicKeyString(String email) throws ExecutionException, InterruptedException {
        Log.i("ServerUtilities", "askPublicKeyString - begin with email = " + email);
        String public_key_string = "init";

        SendAndReceive send_and_receive = new SendAndReceive();
        public_key_string = send_and_receive.execute(email).get();

        Log.i("ServerUtilities", "askPublicKeyString - return from async = " + public_key_string);

        return public_key_string;
    }

    static class SendAndReceive extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... email) {
            Log.i("ServerUtilities", "SendAndReceive - doInBackground with email[0] = " + email[0]);
            String public_key_string = "initial public_key_string";
            String serverUrl = Common.getServerUrl() + "/getUserInfo";
            Map<String, String> params = new HashMap<String, String>();

            params.put(DataProvider.USER_INFO, "public_key_request");
            params.put(DataProvider.RECEIVER_EMAIL, email[0]);

            try {

                Log.i("ServerUtilities", "askPublicKeyString - vor response from server");
                String text_response_from_server = postWithResponse(serverUrl, params, MAX_ATTEMPTS);
                Log.i("ServerUtilities", "askPublicKeyString - after response from server");


                //conn.disconnect();

                return text_response_from_server;

            } catch (IOException e) {
                public_key_string = "io exception";
                return public_key_string;
            } catch (Exception e) {
                public_key_string = "unknown exception";
                return public_key_string;
            }
        }

    }




	/**
	 * Send a message.
	 */
	public static void send(String msg, String to) throws IOException {

		String serverUrl = Common.getServerUrl() + "/send";
		Map<String, String> params = new HashMap<String, String>();
		params.put(DataProvider.MESSAGE, msg);
		params.put(DataProvider.SENDER_EMAIL, Common.getPreferredEmail());
		params.put(DataProvider.RECEIVER_EMAIL, to);        
		post(serverUrl, params, MAX_ATTEMPTS);
	}


	/** Issue a POST with exponential backoff */
	private static void post(String endpoint, Map<String, String> params, int maxAttempts) throws IOException {
		long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
		for (int i = 1; i <= maxAttempts; i++) {

			try {
				post(endpoint, params);
				return;
			} catch (IOException e) {

				if (i == maxAttempts) {
					throw e;
				}
				try {
					Thread.sleep(backoff);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					return;
				}
				backoff *= 2;    			
			} catch (IllegalArgumentException e) {
				throw new IOException(e.getMessage(), e);
			}
		}
	}

	/**
	 * Issue a POST request to the server.
	 *
	 * @param endpoint POST address.
	 * @param params request parameters.
	 *
	 * @throws java.io.IOException propagated from POST.
	 */
	private static void post(String endpoint, Map<String, String> params) throws IOException {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();

		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();

			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}







    /** Issue a POST with exponential backoff */
    private static String postWithResponse(String endpoint, Map<String, String> params, int maxAttempts) throws IOException {
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        Log.i("ServerUtilities", "postWithResponse1 - begin");

        for (int i = 1; i <= maxAttempts; i++) {

            try {

                return postWithResponse(endpoint, params);
            } catch (IOException e) {

                if (i == maxAttempts) {
                    throw e;
                }
                try {
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    Thread.currentThread().interrupt();
                    return "not working";
                }
                backoff *= 2;
            } catch (IllegalArgumentException e) {
                throw new IOException(e.getMessage(), e);
            }
        }
        return "not working2";
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws java.io.IOException propagated from POST.
     */
    private static String postWithResponse(String endpoint, Map<String, String> params) throws IOException {
        URL url;
        Log.i("ServerUtilities", "postWithResponse2 - begin");

        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();

        byte[] bytes = body.getBytes();
        Log.i("ServerUtilities", "postWithResponse2 - body wurde zu bytes");

        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            Log.i("ServerUtilities", "postWithResponse2 - connection opened");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            Log.i("ServerUtilities", "postWithResponse2 - outputstream written");

            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            } else {
                Log.i("ServerUtilities", "postWithResponse2 - send was sucessfully");
            }
            Log.d("ServerUtilities", "postWithResponse2 - getHeaderField = " + conn.getHeaderField(DataProvider.PUB_KEY));
            return conn.getHeaderField(DataProvider.PUB_KEY);
        } catch (Exception e) {
            Log.d("ServerUtilities", "postWithResponse2 - exception = " + e.toString());
            if (conn != null) {
                conn.disconnect();
            }
            return "not working3";
        } finally {
            Log.d("ServerUtilities", "postWithResponse2 - finally");
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
