package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.adapters.ExpertPlayerListAdapter;
import com.plego.wagerocity.android.model.ExpertPlayer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.ExpertsFragment.OnExpertsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpertsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpertsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_EXPERT_PLAYERS = "args_expert_players";
    private ArrayList<ExpertPlayer> expertPlayers;

    private OnExpertsFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param expertPlayers Parameter 1.
     * @return A new instance of fragment ExpertsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpertsFragment newInstance(ArrayList<ExpertPlayer> expertPlayers) {
        ExpertsFragment fragment = new ExpertsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_EXPERT_PLAYERS, expertPlayers);
        fragment.setArguments(args);
        return fragment;
    }

    public ExpertsFragment() {
        // Required empty public constructor
    }
//
//    public ExpertsFragment (ArrayList<ExpertPlayer> expertPlayers) {
//        this.expertPlayers = expertPlayers;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView expertsListView = (ListView) view.findViewById(R.id.listview_experts);

        ExpertPlayerListAdapter expertPlayerListAdapter = new ExpertPlayerListAdapter(this.expertPlayers, view.getContext());

        expertsListView.setAdapter(expertPlayerListAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.expertPlayers = new ArrayList<>();
        if (getArguments() != null) {
            this.expertPlayers = getArguments().getParcelableArrayList(ARGS_EXPERT_PLAYERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_experts, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onExpertsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnExpertsFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnExpertsFragmentInteractionListener");
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
    public interface OnExpertsFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onExpertsFragmentInteraction(Uri uri);
    }

}
