package com.plego.wagerocity.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.*;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.utils.AndroidUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hassan on 9/15/2015.
 */
public class CreatePoolFragment extends Fragment {

	public static final String TAG = CreatePoolFragment.class.getSimpleName();

	public static Fragment newInstance () {
		return new CreatePoolFragment();
	}

	@Bind(R.id.edt_pool_name)
	EditText   edtPoolName;
	@Bind(R.id.edt_pool_motto)
	EditText   edtPoolMotto;
	@Bind(R.id.edt_pool_desc)
	EditText   edtPoolDesc;
	@Bind(R.id.radio_pool_privacy)
	RadioGroup radioGroupPoolPrivacy;
	@Bind(R.id.radio_pool_size)
	RadioGroup radioGroupPoolSize;
	@Bind(R.id.edt_min_pool_size)
	EditText   edtMinPoolSize;
	@Bind(R.id.edt_amount)
	EditText   edtAmount;
	@Bind(R.id.edt_from_date)
	EditText   edtFromDate;
	@Bind(R.id.edt_to_date)
	EditText   edtToDate;
	@Bind(R.id.spinner_sport)
	Spinner spinnerSport;
	List<String> sports;

	@Nullable
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_create_pool, container, false );
		ButterKnife.bind( this, view );
		return view;
	}

	@Override
	public void onDestroyView () {
		ButterKnife.unbind( this );
		super.onDestroyView();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		sports = new ArrayList<>();
		sports.add("NFL");
		sports.add("NCAA Football");
		sports.add("MLB");
		sports.add("NBA");
		sports.add("NCAA Basketball");
		sports.add("NHL");
		sports.add("Soccer");
		sports.add("Tennis");
		spinnerSport.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sports));
	}

	@OnClick(R.id.btn_create_pool)
	public void createPool() {
		String userId = new WagerocityPref(getActivity()).user().getUserId();
		String poolName = AndroidUtils.getText(edtPoolName);
		String poolMotto = AndroidUtils.getText(edtPoolMotto);
		String poolDesc = AndroidUtils.getText(edtPoolDesc);
		String minPoolSize = AndroidUtils.getText(edtMinPoolSize);
		String amount = AndroidUtils.getText(edtAmount);
		String fromDate = AndroidUtils.getText(edtFromDate);
		String toDate = AndroidUtils.getText(edtToDate);
		String poolPrivacy = radioGroupPoolPrivacy.getCheckedRadioButtonId() == R.id.radio_open_pool ? "open" : "close";
		String poolSize = radioGroupPoolSize.getCheckedRadioButtonId() == R.id.radio_unlimited_size ? "UNLIMITED" : "LIMIT";
		String sportName = sports.get(spinnerSport.getSelectedItemPosition());
		String leagueId = AndroidUtils.getSportsIdForParam(sportName);
		String leagueName = AndroidUtils.getSportsNameForParam(sportName);
		new RestClient().getApiService()
						.createPool( userId, poolName, poolMotto, poolDesc, poolPrivacy, poolSize,
									 minPoolSize, amount, toDate, fromDate, leagueId, leagueName, "",
									 new CreatePoolCallback() );
	}

	private class CreatePoolCallback implements Callback<Pool> {
		@Override
		public void success(Pool pool, Response response) {
			Log.d(TAG, "Pool create successfully: " + response.getBody().toString());
		}

		@Override
		public void failure(RetrofitError error) {
			Log.e(TAG, "Failed to create pool: ", error);
		}
	}
}
