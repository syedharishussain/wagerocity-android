package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityApplication;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.PoolsListAdapter;
import com.plego.wagerocity.android.controller.events.*;
import com.plego.wagerocity.android.model.*;
import com.plego.wagerocity.android.services.GetUserService;
import com.plego.wagerocity.utils.AndroidUtils;
import com.plego.wagerocity.utils.UiUtils;
import com.squareup.otto.Subscribe;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.PoolsFragment.OnPoolsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PoolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoolsFragment extends Fragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARGS_POOLS = "args_pools";

	private ArrayList<Pool>                    pools;
	private OnPoolsFragmentInteractionListener mListener;
	private SweetAlertDialog                   pDialog;
	private PoolsListAdapter                   poolsListAdapter;
	@Inject
	EventFactory   eventFactory;
	@Inject
	ServiceModel   serviceModel;
	@Inject
	UiUtils        uiUtils;
	@Inject
	WagerocityPref wagerocityPref;
	private SweetAlertDialog progressAlert;


	public PoolsFragment () {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment PoolsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PoolsFragment newInstance () {
		PoolsFragment fragment = new PoolsFragment();
		Bundle args = new Bundle();
//		args.putParcelableArrayList( ARGS_POOLS, pools );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onResume () {
		super.onResume();
		eventFactory.register( this );
	}

	@Override
	public void onPause () {
		super.onPause();
		eventFactory.unregister( this );
	}

	@Override
	public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated( view, savedInstanceState );

		WagerocityApplication.component( getActivity() ).inject( this );
		uiUtils.setContext( getActivity() );

		Button showMyPoolsButton = (Button) view.findViewById( R.id.button_show_my_pools );
		showMyPoolsButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick (View v) {
				Uri uri = Uri.parse( getString( R.string.uri_open_my_pools_fragment ) );
				mListener.onPoolsFragmentInteraction( uri );
			}
		} );

		final ListView poolsListView = (ListView) view.findViewById( R.id.listview_pools );

		poolsListAdapter = new PoolsListAdapter( pools, getActivity(), false );

		poolsListView.setAdapter( poolsListAdapter );
		getAllPools();
	}

	private void getAllPools () {
		pDialog = AndroidUtils.showDialog(
				getString( R.string.loading ),
				null,
				SweetAlertDialog.PROGRESS_TYPE,
				getActivity()
		);

		RestClient restClient = new RestClient();
		restClient.getApiService()
				.getAllPools( new WagerocityPref( getActivity() ).user().getUserId(), new Callback<ArrayList<Pool>>() {
					@Override
					public void success (ArrayList<Pool> pools, retrofit.client.Response response) {
						pDialog.dismiss();
						if (pools != null) {
							PoolsFragment.this.pools.clear();
							PoolsFragment.this.pools.addAll( pools );
							poolsListAdapter.notifyDataSetChanged();
						}
					}

					@Override
					public void failure (RetrofitError error) {
						pDialog.dismiss();
						Log.e( "getAllPools", String.valueOf( error ) );

						AndroidUtils.showErrorDialog( error, getActivity() );
					}

				} );
	}


	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		if (getArguments() != null) {
			pools = new ArrayList<>();
//			this.pools = getArguments().getParcelableArrayList( ARGS_POOLS );
		}
	}

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
							  Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate( R.layout.fragment_pools, container, false );
		ButterKnife.bind( this, view );
		return view;
	}

	@Override
	public void onDestroyView () {
		ButterKnife.unbind( this );
		super.onDestroyView();
	}

	@Subscribe
	public void handlePoolItemAction (OnItemEvent itemEvent) {
		if (itemEvent.getAction() == Action.JOIN_POOL) {
			final Pool pool = pools.get( itemEvent.getData() );
			if (Integer.parseInt( pool.getAmount() ) > 0) {
				String content = "$" + pool
						.getAmount() + " will be deducted from your account, do you still want to join?";
				uiUtils.showWarningAlert( "Are you sure?", content, new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick (SweetAlertDialog sweetAlertDialog) {
						sweetAlertDialog.dismissWithAnimation();
						joinPool( pool );
					}
				} );
			} else {
				joinPool( pool );
			}
		}
	}

	private void joinPool(Pool pool) {
		progressAlert = uiUtils.showProgressAlert( "Loading" );
		String userId = wagerocityPref.user().getUserId();
		String poolId = pool.getPoolId();
		serviceModel.joinPool( userId, poolId, new Callback<ArrayList<Pool>>() {
			@Override
			public void success (ArrayList<Pool> pools, Response response) {
				new GetUserService( getActivity() ).get();
				progressAlert.dismiss();
				Uri uri = Uri.parse( getString( R.string.uri_open_my_pools_fragment ) );
				mListener.onPoolsFragmentInteraction( uri );
			}

			@Override
			public void failure (RetrofitError error) {
				progressAlert.dismiss();
				uiUtils.showErrorDialog( error );
			}
		} );
	}

	@OnClick(R.id.btn_create_pool)
	public void showCreatePool () {
		mListener.onPoolsFragmentInteraction( Uri.parse( getString( R.string.uri_open_create_pool ) ), null );
	}

	@Override
	public void onAttach (Activity activity) {
		super.onAttach( activity );
		try {
			mListener = (OnPoolsFragmentInteractionListener) activity;
		}
		catch (ClassCastException e) {
			throw new ClassCastException( activity.toString()
					+ " must implement OnPoolsFragmentInteractionListener" );
		}
	}

	@Override
	public void onDetach () {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnPoolsFragmentInteractionListener {

		// TODO: Update argument type and name
		public void onPoolsFragmentInteraction (Uri uri, ArrayList<MyPool> pools);
		public void onPoolsFragmentInteraction (Uri uri);
	}

}
