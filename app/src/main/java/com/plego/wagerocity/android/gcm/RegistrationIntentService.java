/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.plego.wagerocity.android.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.ServiceModel;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.IOException;

import javax.inject.Inject;

public class RegistrationIntentService extends IntentService {

	private static final String   TAG    = RegistrationIntentService.class.getSimpleName();
	private static final String[] TOPICS = {"global"};

	@Inject ServiceModel   serviceModel;
	@Inject WagerocityPref prefs;

	public RegistrationIntentService () {
		super( TAG );
	}

	@Override
	protected void onHandleIntent (Intent intent) {
		final InstanceID instanceID = InstanceID.getInstance( this );
		final GcmPubSub pubSub = GcmPubSub.getInstance( this );
		new Thread( new Runnable() {
			@Override public void run () {
				try {
					String token = instanceID.getToken( getString( R.string.gcm_defaultSenderId ),
														GoogleCloudMessaging.INSTANCE_ID_SCOPE, null );
					subscribeTopics( token, pubSub );
					serviceModel.setDeviceToken( prefs.user()
													  .getUserId(), token, 2, new Callback<Response>() {

						@Override public void success (Response response, Response response2) {
							prefs.setSentTokenToServer( true );
						}

						@Override public void failure (RetrofitError error) {
							prefs.setSentTokenToServer( false );
							Log.e( TAG,
								   "Failed to registerDeviceToken to server",
								   error );
						}
					} );
				}
				catch (IOException e) {
					prefs.setSentTokenToServer( false );
					e.printStackTrace();
				}
			}
		} ).start();
	}

	private void subscribeTopics (String token, GcmPubSub pubSub) throws IOException {
		for (String topic : TOPICS) {
			pubSub.subscribe( token, "/topics/" + topic, null );
		}
	}
}
