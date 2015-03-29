package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.adapters.GamesListAdapter;
import com.plego.wagerocity.android.model.Game;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.plego.wagerocity.android.fragments.GamesListFragment.OnGamesListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GamesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GamesListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARGS_GAMES_LIST = "games_list";

    private ArrayList<Game> games;

    private OnGamesListFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param games Parameter 1.
     * @return A new instance of fragment GamesListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GamesListFragment newInstance(ArrayList<Game> games) {
        GamesListFragment fragment = new GamesListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_GAMES_LIST, games);
        fragment.setArguments(args);
        return fragment;
    }

    public GamesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            games = getArguments().getParcelableArrayList(ARGS_GAMES_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView gamesListView = (ListView) view.findViewById(R.id.listview_games_list);

//        ArrayList<String> gamesList = new ArrayList();
//
//        for (Game game: games) {
//            gamesList.add(game.getTeamAName());
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
//                R.layout.layout_cell_games_list, R.id.textview_cell_games_team_a, gamesList);

        GamesListAdapter gamesListAdapter = new GamesListAdapter(view.getContext(), games);

        gamesListView.setAdapter(gamesListAdapter);


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGamesListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGamesListFragmentInteractionListener");
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
    public interface OnGamesListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onGamesListFragmentInteraction(Uri uri);
    }

}
