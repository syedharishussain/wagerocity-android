package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.android.util.IabException;
import com.plego.wagerocity.android.util.IabHelper;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.event.OnActivityResultEvent;
import roboguice.event.Observes;
import roboguice.fragment.RoboFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetDollarsFragment.OnGetDollarsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetDollarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetDollarsFragment extends RoboFragment implements BillingProcessor.IBillingHandler {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private OnGetDollarsFragmentInteractionListener mListener;
    BillingProcessor bp;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetDollarsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GetDollarsFragment newInstance(String param1, String param2) {
        GetDollarsFragment fragment = new GetDollarsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public GetDollarsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bp = new BillingProcessor(getActivity(), getActivity().getString(R.string.in_app_billing_public_key), this);

        Button get2000DollarsButton = (Button) view.findViewById(R.id.button_get_dollars_rookie);
        get2000DollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bp.consumePurchase(StringConstants.IAB_TEST);
//                boolean isPurchased = bp.isPurchased(StringConstants.IAB_TEST);
//
//                if (isPurchased) {
//                }
////                bp.purchase(getActivity(), getActivity().getString(R.string.in_app_billing_rookie));
//                bp.purchase(getActivity(), StringConstants.IAB_TEST);

                mListener.onGetDollarsFragmentInteraction(StringConstants.IAB_ROOKIE);

            }
        });

        Button getChaserDollarsButton = (Button) view.findViewById(R.id.button_get_dollars_chaser);
        getChaserDollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bp.purchase(getActivity(), StringConstants.IAB_CHASER);
//                bp.consumePurchase(StringConstants.IAB_CHASER);
//                buyCreditsAPI((float)6250.0);
                mListener.onGetDollarsFragmentInteraction(StringConstants.IAB_CHASER);
            }
        });

        Button getPlayerDollarsButton = (Button) view.findViewById(R.id.button_get_dollars_player);
        getPlayerDollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bp.purchase(getActivity(), StringConstants.IAB_PLAYER);
//                bp.consumePurchase(StringConstants.IAB_PLAYER);
//                buyCreditsAPI((float)30000.0);
                mListener.onGetDollarsFragmentInteraction(StringConstants.IAB_PLAYER);
            }
        });

        Button getGuruDollarsButton = (Button) view.findViewById(R.id.button_get_dollars_guru);
        getGuruDollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bp.purchase(getActivity(), StringConstants.IAB_GURU);
//                bp.consumePurchase(StringConstants.IAB_GURU);
//                buyCreditsAPI((float)87500.0);
                mListener.onGetDollarsFragmentInteraction(StringConstants.IAB_GURU);
            }
        });

        Button getBawseDollarsButton = (Button) view.findViewById(R.id.button_get_dollars_bawse);
        getBawseDollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bp.purchase(getActivity(), StringConstants.IAB_BAWSE);
//                bp.consumePurchase(StringConstants.IAB_BAWSE);
//                buyCreditsAPI((float)200000.0);
                mListener.onGetDollarsFragmentInteraction(StringConstants.IAB_BAWSE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_dollars, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGetDollarsFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGetDollarsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void buyCreditsAPI (Float credits) {

        final WagerocityPref pref = new WagerocityPref(getActivity());
        final User user = pref.user();

        final SweetAlertDialog pDialog = AndroidUtils.showDialog(
                getString(R.string.loading),
                null,
                SweetAlertDialog.PROGRESS_TYPE,
                getActivity()
        );

        new RestClient().getApiService().buyCredits(user.getUserId(), credits, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                pDialog.dismiss();
                pref.setUser(user);
                AndroidUtils.updateStats(getActivity());
            }

            @Override
            public void failure(RetrofitError error) {
                pDialog.dismiss();
                AndroidUtils.showErrorDialog(error, getActivity());
            }
        });
    }

    public void onActivityResultFromActivity (@Observes OnActivityResultEvent onActivityResultEvent) {
        Log.i("@Observes", "In GetDollars Fragment:" + onActivityResultEvent.getRequestCode());
        if (!bp.handleActivityResult(onActivityResultEvent.getRequestCode(), onActivityResultEvent.getResultCode(), onActivityResultEvent.getData()))
            super.onActivityResult(onActivityResultEvent.getRequestCode(), onActivityResultEvent.getResultCode(), onActivityResultEvent.getData());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("In App Billing", "On Activity Result in Fragment Request Code:" + requestCode);

        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getFragmentManager();
        GetDollarsFragment fragment = (GetDollarsFragment) fragmentManager.findFragmentByTag(StringConstants.TAG_FRAG_GET_DOLLARS);
        if (fragment != null)
        {
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
        Log.i("In App Billing", transactionDetails.productId);
        if (transactionDetails.productId.equals("android.test.purchased")) buyCreditsAPI((float)2000.0);

        String productId = transactionDetails.productId;

        if (productId.equals(StringConstants.IAB_ROOKIE)) {
            buyCreditsAPI((float)2000.0);
        } else if (productId.equals(StringConstants.IAB_CHASER)) {
            buyCreditsAPI((float)6500.0);
        } else if (productId.equals(StringConstants.IAB_PLAYER)) {
            buyCreditsAPI((float)30000.0);
        } else if (productId.equals(StringConstants.IAB_GURU)) {
            buyCreditsAPI((float)87500.0);
        } else if (productId.equals(StringConstants.IAB_BAWSE)) {
            buyCreditsAPI((float)200000.0);
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
    public interface OnGetDollarsFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGetDollarsFragmentInteraction(String purchaseID);
    }

}
