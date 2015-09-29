package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityApplication;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.MyPoolsListAdapter;
import com.plego.wagerocity.android.model.MyPool;
import com.plego.wagerocity.android.model.ServiceModel;
import com.plego.wagerocity.utils.UiUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.MyPoolsFragment.OnMyPoolsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPoolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPoolsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_MY_POOLS = "args_pool";
    public static final String TAG = MyPoolsFragment.class.getSimpleName();

    private ArrayList<MyPool> pools;

    private InteractionListener mListener;
    @Inject
    ServiceModel   serviceModel;
    @Inject
    WagerocityPref wagerocityPref;
    @Inject
    UiUtils        uiUtils;
    private MyPoolsListAdapter poolsListAdapter;
    private SweetAlertDialog   progressAlert;

    public static MyPoolsFragment newInstance (ArrayList<MyPool> pools) {
        MyPoolsFragment fragment = new MyPoolsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList( ARGS_MY_POOLS, pools );
        fragment.setArguments( args );
        return fragment;
    }

    public static MyPoolsFragment newInstance () {
        return new MyPoolsFragment();
    }

    public MyPoolsFragment () {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated (View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );

        WagerocityApplication.component( getActivity() ).inject( this );
        uiUtils.setContext( getActivity() );

        final ListView poolsListView = (ListView) view.findViewById( R.id.listview_my_pools );
        poolsListAdapter = new MyPoolsListAdapter( pools, getActivity(), true );
        poolsListView.setAdapter( poolsListAdapter );

        progressAlert = uiUtils.showProgressAlert( "Loading..." );
        serviceModel.getMyPools( new WagerocityPref( getActivity() ).user()
                .getUserId(), new Callback<ArrayList<MyPool>>() {
            @Override
            public void success (ArrayList<MyPool> myPools, Response response) {
                progressAlert.dismissWithAnimation();
                pools.clear();
                pools.addAll( myPools );
                poolsListAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure (RetrofitError error) {
                Log.e( TAG, "Failed to load my pools", error );
                progressAlert.dismissWithAnimation();
                SweetAlertDialog sweetAlertDialog = uiUtils.showErrorDialog( error );
                sweetAlertDialog.setOnDismissListener( new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss (DialogInterface dialog) {
                        mListener.goBack();
                    }
                } );
            }
        } );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pools = getArguments().getParcelableArrayList( ARGS_MY_POOLS );
        } else {
            pools = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_pools, container, false);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (InteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMyPoolsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
