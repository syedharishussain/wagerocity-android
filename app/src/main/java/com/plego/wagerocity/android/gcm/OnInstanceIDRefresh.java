package com.plego.wagerocity.android.gcm;

import android.content.Intent;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Hassan Jawed on 9/30/2015.
 */
public class OnInstanceIDRefresh extends InstanceIDListenerService {

	public static final String TAG = OnInstanceIDRefresh.class.getSimpleName();
	private static final String[] TOPICS = {"global"};

	public void onTokenRefresh () {
		Intent intent = new Intent( this, RegistrationIntentService.class );
		startService( intent );
	}
}
