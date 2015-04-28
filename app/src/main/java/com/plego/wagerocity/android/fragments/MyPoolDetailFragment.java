package com.plego.wagerocity.android.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plego.wagerocity.R;
import com.plego.wagerocity.android.model.MyPool;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPoolDetailFragment.OnMyPoolDetailFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPoolDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPoolDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private MyPool pool;


    private OnMyPoolDetailFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param myPool .
     * @return A new instance of fragment MyPoolDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPoolDetailFragment newInstance(MyPool myPool) {
        MyPoolDetailFragment fragment = new MyPoolDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, myPool);
        fragment.setArguments(args);
        return fragment;
    }

    public MyPoolDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pool = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_pool_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMyPoolDetailFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnMyPoolDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onMyPoolDetailFragmentInteraction(Uri uri);
    }

}
