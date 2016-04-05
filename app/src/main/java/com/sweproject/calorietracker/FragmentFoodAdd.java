package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Marcus on 2/18/2016.
 */
public class FragmentFoodAdd extends Fragment implements View.OnClickListener {

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		TextView title = (TextView) getActivity().findViewById(R.id.fragmet_food_title);
		Spinner dropDown = (Spinner) getActivity().findViewById(R.id.fragment_food_spinner_serving);
		Spinner dropDown2 = (Spinner) getActivity().findViewById(R.id.fragment_food_spinner_serving_type);
		Button submit = (Button) getActivity().findViewById(R.id.fragment_food_btn_submit);

		String[] type = {"Grams", "Ounce"};
		String[] size = {"1", "2", "3", "4", "5"};

		FragmentDay.sAddedFoodList.add(FragmentFoodSearch.sDBFoodList.get(getArguments().getInt("Food")));

		title.setText(FragmentFoodSearch.sDBFoodList.get(getArguments().getInt("Food")).getName());
		dropDown.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, size));
		dropDown2.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, type));
		submit.setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_food_add, container, false);
	}

	@Override
	public void onClick(View view) {
		// GetArguments returns the date back to the Day fragment
		MainActivity.nextFragment(this, new FragmentDay(), getArguments(), true, true);
	}

}
