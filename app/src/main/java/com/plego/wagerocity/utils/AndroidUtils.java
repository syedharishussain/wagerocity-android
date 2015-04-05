package com.plego.wagerocity.utils;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.constants.StringConstants;

import junit.framework.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by haris on 11/02/15.
 */
public class AndroidUtils {

    public static void printFBKeyHash (Activity activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), PackageManager.GET_SIGNATURES );
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance( "SHA" );
                md.update( signature.toByteArray() );
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (PackageManager.NameNotFoundException e) {

        }
        catch (NoSuchAlgorithmException e) {

        }
    }

    public static Fragment getFragmentByTag (FragmentActivity activity, String TAG) {
        return (Fragment) activity
                .getSupportFragmentManager()
                .findFragmentByTag(StringConstants.TAG_FRAG_STATS);
    }

    public static void updateStats (FragmentActivity activity) {
        StatsFragment statsFragment = (StatsFragment) AndroidUtils.
                getFragmentByTag(activity, StringConstants.TAG_FRAG_STATS);

        statsFragment.updateStats();
    }

    public static String getSportsNameForParam (String sportsName) {

        if (sportsName.equals("NFL")) {
            return "nfl";
        } else if (sportsName.equals("NCAA Football")) {
            return "ncaaf";
        } else if (sportsName.equals("MLB")) {
            return "mlb";
        } else if (sportsName.equals("NBA")) {
            return "nba";
        } else if (sportsName.equals("NCAA Basketball")) {
            return "ncaab";
        } else if (sportsName.equals("NHL")) {
            return "nhl";
        } else if (sportsName.equals("Soccer")) {
            return "soccer";
        } else if (sportsName.equals("Tennis")) {
            return "tennis";
        }

        return "";
    }
}
