package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.adapters.SportsListAdapter;
import com.plego.wagerocity.android.model.Game;
import com.plego.wagerocity.android.model.RestClient;
import com.plego.wagerocity.android.model.SportsListObject;
import com.plego.wagerocity.utils.AndroidUtils;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportsListFragment.OnSportsListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SportsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportsListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnSportsListFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SportsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SportsListFragment newInstance(String param1, String param2) {
        SportsListFragment fragment = new SportsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SportsListFragment() {
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView sportsListView = (ListView) view.findViewById(R.id.listview_sports_list);

        SportsListAdapter sportsListAdapter = new SportsListAdapter(view.getContext(), getSportsListData());
        sportsListView.setAdapter(sportsListAdapter);

        sportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String sportsName = ((SportsListObject) parent.getItemAtPosition(position)).getSportsName();

                Log.e("Selected Sports", sportsName);

                final SweetAlertDialog pDialog = AndroidUtils.showDialog(
                        getString(R.string.loading_games),
                        getString(R.string.please_wait),
                        SweetAlertDialog.PROGRESS_TYPE,
                        getActivity()
                );

                RestClient restClient = new RestClient();
                restClient.getApiService().getGames(AndroidUtils.getSportsNameForParam(sportsName), new Callback<ArrayList<Game>>() {
                    @Override
                    public void success(ArrayList<Game> games, Response response) {

                        pDialog.dismiss();

                        if (games.size() > 0) {

                            Uri uri = Uri.parse(getString(R.string.uri_open_games_list_fragment));
                            mListener.onSportsListFragmentInteraction(uri, games, AndroidUtils.getSportsNameForParam(sportsName));

                        } else {


                            SweetAlertDialog pDialog = AndroidUtils.showDialog(
                                    getString(R.string.no_games_found),
                                    "There are no " + sportsName + " games going on for now. Please come back later",
                                    SweetAlertDialog.ERROR_TYPE,
                                    getActivity()
                            );
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pDialog.dismiss();

                        AndroidUtils.showDialog(
                                getString(R.string.no_games_found),
                                "There are no " + sportsName + " games going on for now. Please come back later",
                                SweetAlertDialog.ERROR_TYPE,
                                getActivity()
                        );
                    }
                });
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sports_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSportsListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSportsListFragmentInteractionListener");
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
    public interface OnSportsListFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onSportsListFragmentInteraction(Uri uri, ArrayList<Game> games, String sportsNameValueForParam);
    }

    private ArrayList<SportsListObject> getSportsListData() {
        ArrayList<SportsListObject> sportsListObjects = new ArrayList<SportsListObject>();

        sportsListObjects.add(new SportsListObject("NFL", R.drawable.nfl));
        sportsListObjects.add(new SportsListObject("NCAA Football", R.drawable.ncaa_football));
        sportsListObjects.add(new SportsListObject("MLB", R.drawable.mlb));
        sportsListObjects.add(new SportsListObject("NBA", R.drawable.nba));
        sportsListObjects.add(new SportsListObject("NCAA Basketball", R.drawable.ncaa_basketball));
        sportsListObjects.add(new SportsListObject("NHL", R.drawable.nhl));
        sportsListObjects.add(new SportsListObject("Soccer", R.drawable.soccer));
        sportsListObjects.add(new SportsListObject("Tennis", R.drawable.tennis));

        return sportsListObjects;
    }

}

