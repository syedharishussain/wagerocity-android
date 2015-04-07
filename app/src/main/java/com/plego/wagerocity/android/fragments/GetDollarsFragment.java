package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GetDollarsFragment.OnGetDollarsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GetDollarsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetDollarsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private OnGetDollarsFragmentInteractionListener mListener;

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

        Button get2000DollarsButton = (Button) view.findViewById(R.id.button_get_dollars_2000);
        get2000DollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyCreditsAPI((float)2000.0);
            }
        });

        Button get15000DollarsButton = (Button) view.findViewById(R.id.button_get_dollars_15000);
        get15000DollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyCreditsAPI((float)15000.0);
            }
        });

        Button get100000DollarsButton = (Button) view.findViewById(R.id.button_get_dollars_100000);
        get100000DollarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buyCreditsAPI((float)100000.0);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_dollars, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onGetDollarsFragmentInteraction(uri);
        }
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
        public void onGetDollarsFragmentInteraction(Uri uri);
    }

}
