package com.plego.wagerocity.android.services;

import android.app.Activity;
import com.plego.wagerocity.android.WagerocityApplication;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.activities.DashboardActivity;
import com.plego.wagerocity.android.model.ServiceModel;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.utils.AndroidUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import javax.inject.Inject;

/**
 * Created by Hassan Jawed on 9/30/2015.
 */
public class GetUserService implements Callback<User> {

	Activity activity;
	@Inject
	ServiceModel   serviceModel;
	@Inject
	WagerocityPref wagerocityPref;


	public GetUserService (Activity activity) {
		this.activity = activity;
		WagerocityApplication.component( activity ).inject( this );
	}

	public void get () {
		serviceModel.getUser( wagerocityPref.facebookID(), this );
	}

	@Override
	public void success (User user, Response response) {
		wagerocityPref.setUser( user );
		AndroidUtils.updateStats( (DashboardActivity) activity );
	}

	@Override
	public void failure (RetrofitError error) {

	}
}
