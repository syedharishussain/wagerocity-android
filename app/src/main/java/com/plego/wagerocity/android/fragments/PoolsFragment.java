package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.PoolsListAdapter;
import com.plego.wagerocity.android.model.*;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

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

	public PoolsFragment () {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param pools Parameter 1.
	 * @return A new instance of fragment PoolsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static PoolsFragment newInstance (ArrayList<Pool> pools) {
		PoolsFragment fragment = new PoolsFragment();
		Bundle args = new Bundle();
		args.putParcelableArrayList( ARGS_POOLS, pools );
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated( view, savedInstanceState );

		Button showMyPoolsButton = (Button) view.findViewById( R.id.button_show_my_pools );
		showMyPoolsButton.setOnClickListener( new View.OnClickListener() {
			@Override
			public void onClick (View v) {

				RestClient restClient = new RestClient();
				restClient.getApiService().getMyPools( new WagerocityPref( getActivity() ).user()
						.getUserId(), new Callback<ArrayList<MyPool>>() {
					@Override
					public void success (ArrayList<MyPool> myPools, Response response) {

						Uri uri = Uri.parse( getString( R.string.uri_open_my_pools_fragment ) );
						mListener.onPoolsFragmentInteraction( uri, myPools );

					}

					@Override
					public void failure (RetrofitError error) {

					}
				} );

			}
		} );

		final ListView poolsListView = (ListView) view.findViewById( R.id.listview_pools );

		PoolsListAdapter poolsListAdapter = new PoolsListAdapter( pools, getActivity(), false );

		poolsListView.setAdapter( poolsListAdapter );

	}


	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		if (getArguments() != null) {
			this.pools = getArguments().getParcelableArrayList( ARGS_POOLS );
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
	}

}
