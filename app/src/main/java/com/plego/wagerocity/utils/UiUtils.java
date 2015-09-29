package com.plego.wagerocity.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.plego.wagerocity.R;
import retrofit.RetrofitError;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
public class UiUtils {

    private Application application;
    private Context     context;

    public UiUtils (Application application) {
        this.application = application;
    }

    public void setContext (Context context) {
        this.context = context;
    }

    public SweetAlertDialog showDialog (String title, String message, int dialogType) {
        SweetAlertDialog pDialog = new SweetAlertDialog( context, dialogType );
        pDialog.getProgressHelper().setBarColor( Color.parseColor( "#A5DC86" ));
        pDialog.setTitleText(title);
        if ( message != null ) pDialog.setContentText(message);
        else pDialog.setContentText(context.getString( R.string.please_wait)) ;
        pDialog.setCancelable(true);
        pDialog.show();

        return pDialog;
    }

    public SweetAlertDialog showProgressAlert (String title) {
        SweetAlertDialog pDialog = new SweetAlertDialog( this.context, SweetAlertDialog.PROGRESS_TYPE );
        pDialog.getProgressHelper().setBarColor( Color.parseColor( "#A5DC86" ) );
        pDialog.setTitleText( title );
        pDialog.setCancelable( false );
        pDialog.show();
        return pDialog;
    }

    public SweetAlertDialog showErrorDialog (RetrofitError error) {
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
        errorDialog.getProgressHelper().setBarColor( Color.parseColor( "#A5DC86" ) );
        errorDialog.setTitleText( errorTitle );
        errorDialog.setContentText( errorMessage );
        errorDialog.setCancelable( true );
        errorDialog.show();
        return errorDialog;
    }

    public SweetAlertDialog showProgressDialog () {
        return showDialog(
                application.getString( R.string.loading ),
                null,
                SweetAlertDialog.PROGRESS_TYPE
        );
    }

    public SweetAlertDialog showWarningAlert (String title, String content, SweetAlertDialog.OnSweetClickListener confirmCallback) {
        SweetAlertDialog warningAlert = new SweetAlertDialog( context, SweetAlertDialog.WARNING_TYPE )
                .setTitleText( title )
                .setContentText( content )
                .setConfirmText( "Yes!" )
                .setConfirmClickListener( confirmCallback )
                .setCancelText( "Cancel" )
                .showCancelButton( true );
        warningAlert.show();
        return warningAlert;
    }
}
