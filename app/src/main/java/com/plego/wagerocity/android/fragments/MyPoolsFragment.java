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
import com.plego.wagerocity.android.adapters.PoolsListAdapter;
import com.plego.wagerocity.android.model.Pool;

import java.util.ArrayList;

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

    private ArrayList<Pool> pools;

    private OnMyPoolsFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pools Parameter 1.
     * @return A new instance of fragment MyPoolsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPoolsFragment newInstance(ArrayList<Pool> pools) {
        MyPoolsFragment fragment = new MyPoolsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARGS_MY_POOLS, pools);
        fragment.setArguments(args);
        return fragment;
    }

    public MyPoolsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ListView poolsListView = (ListView) view.findViewById(R.id.listview_my_pools);

        PoolsListAdapter poolsListAdapter = new PoolsListAdapter(pools, getActivity(), true);

        poolsListView.setAdapter(poolsListAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pools = getArguments().getParcelableArrayList(ARGS_MY_POOLS);
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
            mListener = (OnMyPoolsFragmentInteractionListener) activity;
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
    public interface OnMyPoolsFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMyPoolsFragmentInteraction(Uri uri);
    }

}
