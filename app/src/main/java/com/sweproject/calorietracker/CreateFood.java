package com.sweproject.calorietracker;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jedwa145 on 2/23/2016.
 */


public class CreateFood extends Fragment {
DatabaseHelperClass foodDb;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		foodDb = new DatabaseHelperClass(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.create_food, container, false);

		return root;
	}
}
