package com.plego.wagerocity.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.plego.wagerocity.android.model.User;

/**
 * Created by haris on 23/03/15.
 */
public class WagerocityPref {

    private static final String WAGEROCITY_PREFS = "wagerocity_prefs";

    private final Context context;

    private String facebookID;

    public WagerocityPref(Context context) {
        this.context = context;

    }

    private SharedPreferences getPref() {
        return context.getSharedPreferences(WAGEROCITY_PREFS, Context.MODE_PRIVATE);
    }

    public void setFacebookID(String facebookID) {
        SharedPreferences.Editor pref = getPref().edit();
        pref.putString("userFacebookID", facebookID);
        pref.commit();
    }

    public String facebookID() {
        return getPref().getString("userFacebookID", null);
    }

    public void setFirstName(String firstName) {
        SharedPreferences.Editor pref = getPref().edit();
        pref.putString("userFirstName", firstName);
        pref.commit();
    }

    public String firstName() {
        return getPref().getString("userFirstName", null);
    }

    public void setLastName(String lastName) {
        SharedPreferences.Editor pref = getPref().edit();
        pref.putString("userLastName", lastName);
        pref.commit();
    }

    public String lastName() {
        return getPref().getString("userLastName", null);
    }

    public void setUser(User user) {
        SharedPreferences.Editor pref = getPref().edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        pref.putString("userObject", json);
        pref.commit();
    }

    public User user() {
        Gson gson = new Gson();
        String json = getPref().getString("userObject", null);
        return (User)gson.fromJson(json, User.class);
    }
}
