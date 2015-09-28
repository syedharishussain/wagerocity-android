package com.plego.wagerocity.android;

import android.app.Application;
import android.content.Context;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.plego.wagerocity.BuildConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
public class WagerocityApplication extends Application {

    private BaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerComponentInitializer.initBase(this);
        Crashlytics crashlyticsKit = new Crashlytics.Builder()
                .core( new CrashlyticsCore.Builder().disabled( BuildConfig.ENABLE_CRASHLYTICS ).build() )
                .build();
        Fabric.with(this, crashlyticsKit);
    }

    public static BaseComponent component(Context context) {
        return ((WagerocityApplication) context.getApplicationContext()).component;
    }
}
