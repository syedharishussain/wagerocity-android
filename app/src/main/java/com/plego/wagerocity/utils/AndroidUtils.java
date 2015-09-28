package com.plego.wagerocity.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.*;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.widget.*;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.fragments.StatsFragment;
import com.plego.wagerocity.constants.StringConstants;
import retrofit.RetrofitError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by haris on 11/02/15.
 */
public class AndroidUtils {

    public static String getText(EditText editText) {
        Editable text = editText.getText();
        return text != null ? text.toString() : "";
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || text.length() == 0;
    }

    public static void printFBKeyHash(Activity activity) {
        try {
            PackageInfo info = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static Fragment getFragmentByTag(FragmentActivity activity, String TAG) {
        return (Fragment) activity
                .getSupportFragmentManager()
                .findFragmentByTag(StringConstants.TAG_FRAG_STATS);
    }

    public static void updateStats(FragmentActivity activity) {
        StatsFragment statsFragment = (StatsFragment) AndroidUtils.
                getFragmentByTag(activity, StringConstants.TAG_FRAG_STATS);

        statsFragment.updateStats();
    }

    public static String getSportsNameForParam(String sportsName) {

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

    public static String getSportsIdForParam(String sportsName) {

        if (sportsName.equals("NFL")) {
            return "1";
        } else if (sportsName.equals("NCAA Football")) {
            return "2";
        } else if (sportsName.equals("MLB")) {
            return "5";
        } else if (sportsName.equals("NBA")) {
            return "3";
        } else if (sportsName.equals("NCAA Basketball")) {
            return "4";
        } else if (sportsName.equals("NHL")) {
            return "7";
        } else if (sportsName.equals("Soccer")) {
            return "9";
        } else if (sportsName.equals("Tennis")) {
            return "12";
        }

        return "";
    }

    public static int getDrawableFromLeagueName(String leagueName) {
        if (leagueName == null) {
            return R.drawable.sports;
        }
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
        } else {
            return R.drawable.logo_w;
        }
    }

    public static String getBetTypeFromBetOT(int betOT, String position) {
        switch (betOT) {
            case 1:
                return "MoneyLine";
            case 3:
                return "PointSpread ";
            case 4:
                return position.equals("over") ? "Over" : "Under";
        }
        return "";
    }

    public static int getToWinAmount(double value, double betOddValue) {
        Double result;
        if (betOddValue > 0) {

            double amountNeedToWinADollar = (double) 100 / Math.abs(betOddValue);

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

    public static int getRiskAmount(double value, double betOddValue) {
        Double result;
        if (betOddValue > 0) {
            double amountNeedToWinADollar = Math.abs(betOddValue) / (double) 100;

            double percentage = Math.abs(amountNeedToWinADollar - 1) / amountNeedToWinADollar;

            double percentageBasedValue = value * percentage;

            result = Math.ceil(value - percentageBasedValue);
        } else {
            double amountNeedToWinADollar = (double) 100 / Math.abs(betOddValue);

            double percentage = Math.abs(amountNeedToWinADollar - 1) / amountNeedToWinADollar;

            double percentageBasedValue = value * percentage;

            result = Math.ceil(value + percentageBasedValue);
        }
        return result.intValue();
    }

    public static void showErrorDialog (RetrofitError error, Context context) {

        Boolean isTimedOut = (error.getKind() == RetrofitError.Kind.NETWORK);
        String errorTitle = isTimedOut ? "Error!" : "Error!";
        String errorMessage = isTimedOut ?
                "Fetching results taking too much time. Please try again later." :
                (error.getLocalizedMessage() != null) ?
                        (error.getLocalizedMessage().equals("404 Not Found")) ? "No Records Found." :
                        error.getLocalizedMessage() :
                        (error.getMessage() != null)  ?
                                error.getMessage() :
                                "Something went wrong! Please try again later" ;

        SweetAlertDialog errorDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        errorDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        errorDialog.setTitleText(errorTitle);
        errorDialog.setContentText(errorMessage);
        errorDialog.setCancelable(true);
        errorDialog.show();

    }

    public static SweetAlertDialog showDialog (String title, String message, int dialogType, Context context) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, dialogType);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        if ( message != null ) pDialog.setContentText(message);
        else pDialog.setContentText(context.getString(R.string.please_wait)) ;
        pDialog.setCancelable(true);
        pDialog.show();

        return pDialog;
    }

    public static String getSignedOddValue (String string) {

        if (string == null) {
            return "0";
        }

        if (string.equals("")) return "";

        if (string.equals("-")) return "-";

        if (Double.parseDouble(string) > 0) {
            return "+"+string;
        } else {
            return string;
        }

    }

    public static String getFormatedDate (String dateString) throws ParseException {
        String strCurrentDate = "2015-05-06 18:05:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//    "EEE, dd MMM yyyy hh:mm:ss Z");
        Date newDate = format.parse(dateString); // format.parse(strCurrentDate);
//        Thursday, May 07, 2015
        format = new SimpleDateFormat("EEEE, MMM dd, yyyy hh:mm");
        String date = format.format(newDate);

        date = date + " CST";

        return date;
    }

    public static String getFormatedDateMMHHYYYY (String dateString) throws ParseException {
        String strCurrentDate = "2015-05-06";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//    "EEE, dd MMM yyyy hh:mm:ss Z");
        Date newDate = format.parse(dateString); // format.parse(strCurrentDate);
        format = new SimpleDateFormat("MM-dd-yyyy");
        String date = format.format(newDate);
        return date;
    }
}
