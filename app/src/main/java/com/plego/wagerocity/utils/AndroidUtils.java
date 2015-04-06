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

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.constants.StringConstants;

import junit.framework.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.String.valueOf;

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

    public static int getDrawableFromLeagueName (String leagueName) {
        if (leagueName.equals("NFL") || leagueName.toLowerCase().equals("nfl") || leagueName.toLowerCase().equals("nfl")) {
            return R.drawable.nfl;
        } else if (leagueName.equals("NCAA Football") || leagueName.toLowerCase().equals("ncaa football") || leagueName.toLowerCase().equals("ncaaf")) {
            return R.drawable.ncaa_football;
        } else if (leagueName.equals("MLB") || leagueName.toLowerCase().equals("mlb")) {
            return R.drawable.mlb;
        } else if (leagueName.equals("NBA") || leagueName.toLowerCase().equals("nba")) {
            return R.drawable.nba;
        } else if (leagueName.equals("NCAA Basketball") || leagueName.toLowerCase().equals("ncaa baseketball") || leagueName.toLowerCase().equals("ncaab")) {
            return R.drawable.ncaa_basketball;
        } else if (leagueName.equals("NHL") || leagueName.toLowerCase().equals("nhl")) {
            return R.drawable.nhl;
        } else if (leagueName.equals("Soccer") || leagueName.toLowerCase().equals("soccer")) {
            return R.drawable.soccer;
        } else if (leagueName.equals("Tennis") || leagueName.toLowerCase().equals("tennis")) {
            return R.drawable.tennis;
        }
        return R.drawable.sports;
    }

    public static String getBetTypeFromBetOT (int betOT, String position) {
        switch (betOT) {
            case 1: return "MoneyLine";
            case 3: return "PointSpread ";
            case 4: return position.equals("over") ? "Over" : "Under";
        }
        return "";
    }

    public static int getToWinAmount (double value, double betOddValue) {
        Double result;
        if (betOddValue > 0) {

            double amountNeedToWinADollar =  (double) 100 / Math.abs(betOddValue);

            double percentage = Math.abs(amountNeedToWinADollar - 1) / amountNeedToWinADollar;

            double percentageBasedValue = value * percentage;

            result = Math.ceil(value + percentageBasedValue);

        } else {
            double amountNeedToWinADollar = Math.abs(betOddValue) / (double) 100;

            double percentage = Math.abs(amountNeedToWinADollar - 1) / amountNeedToWinADollar;

            double percentageBasedValue = value * percentage;

            result = Math.ceil(value - percentageBasedValue);
        }
        return result.intValue();
    }
}
