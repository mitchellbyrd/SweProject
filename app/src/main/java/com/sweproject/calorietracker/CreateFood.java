package com.sweproject.calorietracker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
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

		Bundle bun = getArguments();


		Spinner dropDown = (Spinner) getActivity().findViewById(R.id.spinner);
		Spinner dropDown2 = (Spinner) getActivity().findViewById(R.id.spinner2);


		String[] type = {"Grams", "Ounce"};
		String[] size = {"1", "2", "3", "4", "5"};


		dropDown.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, size));
		dropDown2.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, type));

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
