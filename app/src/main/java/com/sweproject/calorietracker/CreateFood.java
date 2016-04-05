package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sweproject.calorietracker.Callbacks.OnDialogDismissListener;
import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.ListViewAdapters.AdapterCreateFood;

import java.util.ArrayList;

/**
 * Created by jedwa145 on 2/23/2016.
 */
public class CreateFood extends Fragment implements View.OnClickListener, OnDialogDismissListener {

	private TextView mFoodName;
	private ListView mCreateList;
	private FloatingActionButton mFab;
	private Button mCreateBtn;

	private ArrayList<ServingSizes> mServingSizes;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		mServingSizes = new ArrayList<>();

		mFoodName = (TextView) getActivity().findViewById(R.id.create_food_name_edit);
		mCreateList = (ListView) getActivity().findViewById(R.id.create_food_listview);
		mFab = (FloatingActionButton) getActivity().findViewById(R.id.create_food_fab);
		mCreateBtn = (Button) getActivity().findViewById(R.id.create_food_btn);

		mFab.setOnClickListener(this);
		mCreateList.setAdapter(new AdapterCreateFood(getActivity(), mServingSizes));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.create_food, container, false);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.create_food_fab:
				DialogServingAdd dialogServingAdd = new DialogServingAdd();
				dialogServingAdd.setOnDismissListener(this);
				dialogServingAdd.show(getActivity().getSupportFragmentManager(), null);
				break;
			case R.id.create_food_btn:
				// Insert food, get id, add id to servingsize and insert that
				break;
		}
	}

	@Override
	public void onDismiss(ServingSizes obj) {
		mServingSizes.add(obj);
		((AdapterCreateFood) mCreateList.getAdapter()).notifyDataSetChanged();
	}
}
