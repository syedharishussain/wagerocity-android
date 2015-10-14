package com.plego.wagerocity.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.plego.wagerocity.R;
import com.plego.wagerocity.utils.AndroidUtils;


import io.fabric.sdk.android.Fabric;
import roboguice.activity.RoboFragmentActivity;


public class SplashActivity extends RoboFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.activity_splash);

		ImageLoader instance = ImageLoader.getInstance();
		if (!instance.isInited()) {
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
			instance.init( config );
		}

		AndroidUtils.printFBKeyHash(this);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startActivity(new Intent(SplashActivity.this, LoginActivity.class));
				finish();
			}
		}, 1000);
	}
}
