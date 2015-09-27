package com.plego.wagerocity.utils;

import android.app.Application;
import android.graphics.Color;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.plego.wagerocity.R;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
public class UiUtils {

    private Application application;

    public UiUtils(Application application) {
        this.application = application;
    }

    public SweetAlertDialog showDialog (String title, String message, int dialogType) {
        SweetAlertDialog pDialog = new SweetAlertDialog(application, dialogType);
        pDialog.getProgressHelper().setBarColor( Color.parseColor( "#A5DC86" ));
        pDialog.setTitleText(title);
        if ( message != null ) pDialog.setContentText(message);
        else pDialog.setContentText( application.getString( R.string.please_wait ) ) ;
        pDialog.setCancelable(true);
        pDialog.show();

        return pDialog;
    }

    public SweetAlertDialog showProgressDialog () {
        return showDialog(
                application.getString( R.string.loading ),
                null,
                SweetAlertDialog.PROGRESS_TYPE
        );
    }
}
