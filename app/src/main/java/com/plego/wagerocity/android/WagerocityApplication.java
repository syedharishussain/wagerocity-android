package com.plego.wagerocity.android;

import android.app.Application;
import android.content.Context;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
public class WagerocityApplication extends Application {

    private BaseComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerComponentInitializer.initBase(this);
    }

    public static BaseComponent component(Context context) {
        return ((WagerocityApplication) context.getApplicationContext()).component;
    }
}
