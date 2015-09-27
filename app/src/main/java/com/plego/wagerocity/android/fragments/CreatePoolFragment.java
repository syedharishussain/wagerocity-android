package com.plego.wagerocity.android.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.Pool;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
	Spinner    spinnerSport;
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
	public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated( view, savedInstanceState );

		sports = new ArrayList<>();
		sports.add( "NFL" );
		sports.add( "NCAA Football" );
		sports.add( "MLB" );
		sports.add( "NBA" );
		sports.add( "NCAA Basketball" );
		sports.add( "NHL" );
		sports.add( "Soccer" );
		sports.add( "Tennis" );
		spinnerSport.setAdapter( new ArrayAdapter<String>( getActivity(),
				android.R.layout.simple_spinner_dropdown_item,
				sports ) );
	}

	@OnClick(R.id.edt_from_date)
	public void showFromDatePicker () {
		DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override public void onDateSet (DatePicker datePicker, int year, int month, int day) {
				Calendar calendar = Calendar.getInstance();
				calendar.set( Calendar.YEAR, year );
				calendar.set( Calendar.MONTH, month );
				calendar.set( Calendar.DAY_OF_MONTH, day );
				SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
				String formattedDate = dateFormat.format( calendar.getTime() );
				edtFromDate.setText( formattedDate );
			}
		};

		Calendar instance = Calendar.getInstance();
		String text = AndroidUtils.getText(edtFromDate);
		if (!AndroidUtils.isEmpty(text)) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parse = simpleDateFormat.parse(text);
				instance.setTime(parse);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		new DatePickerDialog( getActivity(), dateSetListener, instance.get( Calendar.YEAR ), instance.get( Calendar.MONTH ),
				instance.get( Calendar.DAY_OF_MONTH ) ).show();
	}

	@OnClick(R.id.edt_to_date)
	public void showToDatePicker () {
		DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override public void onDateSet (DatePicker datePicker, int year, int month, int day) {
				Calendar calendar = Calendar.getInstance();
				calendar.set( Calendar.YEAR, year );
				calendar.set( Calendar.MONTH, month );
				calendar.set( Calendar.DAY_OF_MONTH, day );
				SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
				String formattedDate = dateFormat.format( calendar.getTime() );
				edtToDate.setText( formattedDate );
			}
		};

		Calendar instance = Calendar.getInstance();
		String text = AndroidUtils.getText(edtToDate);
		if (!AndroidUtils.isEmpty(text)) {
			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date parse = simpleDateFormat.parse(text);
				instance.setTime(parse);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		new DatePickerDialog( getActivity(), dateSetListener, instance.get( Calendar.YEAR ), instance.get( Calendar.MONTH ),
				instance.get( Calendar.DAY_OF_MONTH ) ).show();
	}

	@OnClick(R.id.btn_create_pool)
	public void createPool () {
		String userId = new WagerocityPref( getActivity() ).user()
				.getUserId();
		String poolName = AndroidUtils.getText( edtPoolName );
		String poolMotto = AndroidUtils.getText( edtPoolMotto );
		String poolDesc = AndroidUtils.getText( edtPoolDesc );
		String minPoolSize = AndroidUtils.getText( edtMinPoolSize );
		String amount = AndroidUtils.getText( edtAmount );
		String fromDate = AndroidUtils.getText( edtFromDate );
		String toDate = AndroidUtils.getText( edtToDate );
		String poolPrivacy = radioGroupPoolPrivacy.getCheckedRadioButtonId() == R.id.radio_open_pool ? "open" :
				"private";
		String poolSize =
				radioGroupPoolSize.getCheckedRadioButtonId() == R.id.radio_unlimited_size ? "UNLIMITED" : "LIMIT";
		String sportName = sports.get( spinnerSport.getSelectedItemPosition() );
		String leagueId = AndroidUtils.getSportsIdForParam( sportName );
		String leagueName = AndroidUtils.getSportsNameForParam( sportName );
		new RestClient().getApiService()
				.createPool( userId, poolName, poolMotto, poolDesc, poolPrivacy, poolSize,
						minPoolSize, amount, toDate, fromDate, leagueId, leagueName,
						new CreatePoolCallback() );
	}

	private class CreatePoolCallback implements Callback<Pool> {

		@Override
		public void success (Pool pool, Response response) {
			Log.d( TAG,
					"Pool create successfully: " + response.getBody()
							.toString() );
		}

		@Override
		public void failure (RetrofitError error) {
			Log.e( TAG, "Failed to create pool: ", error );
		}
	}
}
