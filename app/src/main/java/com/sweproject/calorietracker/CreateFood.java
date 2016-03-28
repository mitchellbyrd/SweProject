package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.sweproject.calorietracker.Model.Foods;
import com.sweproject.calorietracker.Model.ServingSizes;

import java.util.ArrayList;

/**
 * Created by jedwa145 on 2/23/2016.
 */
public class CreateFood extends Fragment implements View.OnClickListener {

	private ListView mCreateList;
	private ArrayList<ServingSizes> mServingSizes;
	private ArrayList<Foods> mFoods;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		Bundle bun = getArguments();

		mCreateList = (ListView) getActivity().findViewById(R.id.create_food_listview);
		mCreateList.setAdapter(new AdapterCreateFood(getActivity(), this));

		mServingSizes = new ArrayList<>();
		mFoods = new ArrayList<>();

		//Spinner dropDown = (Spinner) getActivity().findViewById(R.id.create_food_type_spin);
		//Spinner dropDown2 = (Spinner) getActivity().findViewById(R.id.create_food_serving_type_spin);


		String[] type = {"Grams", "Ounce"};
		String[] size = {"1", "2", "3", "4", "5"};


		//dropDown.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, size));
		//dropDown2.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, type));

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.create_food, container, false);
	}

	@Override
	public void onClick(View view) {
		Toast.makeText(getActivity(), "Click", Toast.LENGTH_SHORT).show();
		((AdapterCreateFood) mCreateList.getAdapter()).setCount(5);
	}
}
