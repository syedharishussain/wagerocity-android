package com.plego.wagerocity.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import butterknife.ButterKnife;
import com.plego.wagerocity.R;

/**
 * Created by Hassan on 9/15/2015.
 */
public class CreatePoolFragment extends Fragment {

	public static Fragment newInstance () {
		return new CreatePoolFragment();
	}

	@Nullable
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_create_pool, container, false );
		ButterKnife.bind( this, view );
		return view;
	}

	@Override
	public void onDestroyView () {
		ButterKnife.unbind( this );
		super.onDestroyView();
	}
}
