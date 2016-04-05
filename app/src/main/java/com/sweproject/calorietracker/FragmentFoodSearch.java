package com.sweproject.calorietracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sweproject.calorietracker.Callbacks.DBDataListener;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.ListViewAdapters.AdapterSearchFood;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/3/2016.
 */
public class FragmentFoodSearch extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, DBDataListener {

	private ListView foodList;
	private ProgressBar mProgressBar;
	/**
	 * When a search is performed, food in the db is returned in this list
	 **/
	public static ArrayList<Foods> sDBFoodList = new ArrayList<>();

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		foodList = (ListView) getActivity().findViewById(R.id.fragment_food_search_listview);
		foodList.setAdapter(new AdapterSearchFood(getActivity(), new ArrayList<Foods>()));
		foodList.setOnItemClickListener(this);

		FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fragment_food_search_fab);
		fab.setOnClickListener(this);

		mProgressBar = (ProgressBar) getActivity().findViewById(R.id.fragment_food_search_progress);

		MainActivity.getDBData(Foods.class, this, null, null);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_food_search, container, false);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.fragment_food_search_fab:
				MainActivity.nextFragment(this, new CreateFood(), null, true, false);
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		getArguments().putInt("Food", i);
		MainActivity.nextFragment(this, new FragmentFoodAdd(), getArguments(), true, false);
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		mProgressBar.setVisibility(View.GONE);
		((AdapterSearchFood) foodList.getAdapter()).setData(data);

		FragmentFoodSearch.sDBFoodList.clear();
		for (Object obj : data) {
			FragmentFoodSearch.sDBFoodList.add((Foods)obj); }
	}

	@Override
	public void onBadDataReturn(Exception e) {
		Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
		mProgressBar.setVisibility(View.GONE);
		e.printStackTrace();
	}
}
