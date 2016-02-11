package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Marcus on 2/3/2016.
 */
public class TabFragment extends Fragment implements View.OnClickListener{

	@Override
	public void onActivityCreated(Bundle savedInstance){
		super.onActivityCreated(savedInstance);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_tab, container, false);

		TextView t = (TextView) root.findViewById(R.id.fragment_tab_textview);
		t.setOnClickListener(this);

		return root;
	}

	@Override
	public void onClick(View view) {
		MainActivity.nextFragment(null, new TabFragment(), null, true, false);
	}
}
