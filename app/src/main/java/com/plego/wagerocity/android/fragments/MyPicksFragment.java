package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.Pick;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPicksFragment.OnMyPicksFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPicksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPicksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_MY_PICKS = "my_picks";

    private OnMyPicksFragmentInteractionListener mListener;
    private ArrayList<Pick> picks;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param picks Parameter 1.
     * @return A new instance of fragment MyPicksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPicksFragment newInstance(ArrayList<Pick> picks) {
        MyPicksFragment fragment = new MyPicksFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_MY_PICKS, picks);
        fragment.setArguments(args);
        return fragment;
    }

    public MyPicksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            picks = getArguments().getParcelableArrayList(ARGS_MY_PICKS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_picks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMyPicksFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMyPicksFragmentInteractionListener");
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
    public interface OnMyPicksFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMyPicksFragmentInteraction(Uri uri);
    }

}
