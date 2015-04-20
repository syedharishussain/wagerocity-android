package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.adapters.LeaderboardPlayersListAdapter;
import com.plego.wagerocity.android.adapters.PicksOfPlayerAdapter;
import com.plego.wagerocity.android.model.Game;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.PicksOfPlayerFragment.OnPicksOfPlayerFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PicksOfPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PicksOfPlayerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAMES= "ARGS_GAMES";


    private ArrayList<Game> games;
    private OnPicksOfPlayerFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param games Parameter 1.
     * @return A new instance of fragment PicksOfPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PicksOfPlayerFragment newInstance(ArrayList<Game> games) {
        PicksOfPlayerFragment fragment = new PicksOfPlayerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_GAMES, games);
        fragment.setArguments(args);
        return fragment;
    }

    public PicksOfPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            games = getArguments().getParcelableArrayList(ARGS_GAMES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picks_of_player, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView picksOfPlayerList = (ListView) view.findViewById(R.id.listview_picks_of_player);

        PicksOfPlayerAdapter picksOfPlayerAdapter = new PicksOfPlayerAdapter(this.games, getActivity());

        picksOfPlayerList.setAdapter(picksOfPlayerAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnPicksOfPlayerFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPicksOfFragmentFragmentInteractionListener");
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
    public interface OnPicksOfPlayerFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onPicksOfPlayerFragmentInteraction(Uri uri);
    }

}
