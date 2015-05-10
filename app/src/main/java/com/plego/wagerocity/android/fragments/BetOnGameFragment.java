package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.plego.wagerocity.R;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.adapters.BetSlipAdapter;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.Odd;
import com.plego.wagerocity.android.model.OddHolder;
import com.plego.wagerocity.android.model.Pick;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.User;
import com.plego.wagerocity.constants.StringConstants;
import com.plego.wagerocity.utils.AndroidUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.BetOnGameFragment.OnBetOnGameFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BetOnGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BetOnGameFragment extends Fragment{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAME = "selected_game";

    private ArrayList<OddHolder> oddHolders;
    private OnBetOnGameFragmentInteractionListener mListener;

    public static BetOnGameFragment newInstance(ArrayList<OddHolder> oddHolders) {
        BetOnGameFragment fragment = new BetOnGameFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_GAME, oddHolders);
        fragment.setArguments(args);
        return fragment;
    }

    public BetOnGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            oddHolders = getArguments().getParcelableArrayList(ARGS_GAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bet_on_game, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        final ListView betSlipListView = (ListView) view.findViewById(R.id.listview_betslips);

        final BetSlipAdapter betSlipAdapter = new BetSlipAdapter(oddHolders, view.getContext());

        betSlipListView.setAdapter(betSlipAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBetOnGameFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBetOnGameFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnBetOnGameFragmentInteractionListener {

        public void onBetOnGameFragmentInteraction(Uri uri, ArrayList<Pick> picks);
    }

}

