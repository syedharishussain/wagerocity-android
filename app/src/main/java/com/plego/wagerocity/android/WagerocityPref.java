package com.plego.wagerocity.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;

/**
 * Created by haris on 23/03/15.
 */
@ContextSingleton
public class WagerocityPref {

    private static final String WAGEROCITY_PREFS = "wagerocity_prefs";

    private final Context context;

    @Inject
    public WagerocityPref(Context context) {
        this.context = context;
    }

    private SharedPreferences getPref () {
        return context.getSharedPreferences(WAGEROCITY_PREFS, Context.MODE_PRIVATE);
    }
}
