package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnDashboardFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements BillingProcessor.IBillingHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    BillingProcessor bp;

    private OnDashboardFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fragmentManager = getFragmentManager();
        GetDollarsFragment fragment = (GetDollarsFragment) fragmentManager.findFragmentByTag(StringConstants.TAG_FRAG_GET_DOLLARS);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    @Override
    public void onProductPurchased(String s, TransactionDetails transactionDetails) {
        Log.i("In App Billing", s + " " + transactionDetails.productId);

        if (transactionDetails.productId.equals(StringConstants.IAB_CLEAR_RECORD)) {

        }
    }


    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int i, Throwable throwable) {

    }

    @Override
    public void onBillingInitialized() {

    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bp = new BillingProcessor(getActivity(), getActivity().getString(R.string.in_app_billing_public_key), this);

        Button clearRecordButton = (Button) view.findViewById(R.id.button_clear_balance);
        clearRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDashboardFragmentClearRecordInteraction();
            }
        });

        Button bettingPortalButton = (Button) view.findViewById(R.id.button_betting_portal);
        bettingPortalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_sports_list_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_sports_list_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });

        Button poolsButton = (Button) view.findViewById(R.id.button_pools);
        poolsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_pools_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_pools_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });

        Button myPicksButton = (Button) view.findViewById(R.id.button_my_picks);
        myPicksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_my_picks_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_my_picks_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });

        Button leaderboardListButton = (Button) view.findViewById(R.id.button_leaderboards);
        leaderboardListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_leaderboards_list_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_leaderboards_list_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });

        Button expertsButton = (Button) view.findViewById(R.id.button_experts);
        expertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_experts_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_experts_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });

        Button getDollarsButton = (Button) view.findViewById(R.id.button_get_dollars);
        getDollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_get_dollars_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_get_dollars_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });

        Button getSettingButton = (Button) view.findViewById(R.id.button_get_settings);
        getSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Test", getString(R.string.uri_open_setting_fragment));
                Uri uri = Uri.parse(getString(R.string.uri_open_setting_fragment));
                mListener.onDashboardFragmentInteraction(uri);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDashboardFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDashboardFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDashboardFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
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
    public interface OnDashboardFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDashboardFragmentInteraction(Uri uri);

        public void onDashboardFragmentClearRecordInteraction();
    }

}
