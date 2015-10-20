package com.plego.wagerocity.android.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gms.gcm.GcmListenerService;
import com.google.gson.Gson;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.activities.DashboardActivity;
import com.plego.wagerocity.android.model.BetPayload;
import com.plego.wagerocity.utils.AndroidUtils;

/**
 * Created by Hassan Jawed on 10/10/2015.
 */
public class GcmPushListenerService extends GcmListenerService {


	public static final String TAG = GcmPushListenerService.class.getSimpleName();

	@Override public void onMessageReceived (String from, Bundle data) {
		Log.d( TAG, "from: " + from + "Bundle: " + data );
		Gson gson = new Gson();

		String payload = data.getString( "payload" );
		if (AndroidUtils.isEmpty( payload )) return;

		BetPayload betPayload;
		betPayload = gson.fromJson( payload, BetPayload.class );
		if (betPayload == null) return;

		// TODO replace this with bet result
		String betResult = betPayload.getBetResult();
		boolean isLost;
		switch (betResult.toLowerCase()) {
			case "win":
			case "draw":
				isLost = false;
				break;

			case "loss":
			default:
				isLost = true;
				break;
		}
		String message = getString( isLost ? R.string.text_bet_lost : R.string.text_bet_won,
									betPayload.getStake(), betPayload.getMatchDetail() );
		sendNotification( message );
	}

	private void sendNotification (String message) {
		Intent intent = new Intent( this, DashboardActivity.class );
		intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
		PendingIntent pendingIntent = PendingIntent.getActivity( this, 0 /* Request code */, intent,
																 PendingIntent.FLAG_ONE_SHOT );

		Uri defaultSoundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( this )
				.setSmallIcon( R.drawable.app_icon )
				.setContentTitle( "Wagerocity" )
				.setContentText( message )
				.setAutoCancel( true )
				.setSound( defaultSoundUri )
				.setContentIntent( pendingIntent );

		NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
		bigTextStyle.setBigContentTitle("Wagerocity");
		bigTextStyle.bigText( message );
		notificationBuilder.setStyle(bigTextStyle);

		NotificationManager notificationManager =
				(NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );

		notificationManager.notify( 0 /* ID of notification */, notificationBuilder.build() );
	}
}
