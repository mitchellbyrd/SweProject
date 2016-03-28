package com.sweproject.calorietracker;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.sweproject.calorietracker.Model.Foods;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/3/2016.
 */
public class FragmentFoodSearch extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

	private ArrayList<String> listings = new ArrayList<>();
	private ListView foodList;

	@Override
	public void onActivityCreated(Bundle savedInstance) {
		super.onActivityCreated(savedInstance);

		foodList = (ListView) getActivity().findViewById(R.id.fragment_food_search_listview);
		foodList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listings));
		foodList.setOnItemClickListener(this);

		FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fragment_food_search_fab);
		fab.setOnClickListener(this);

		Toast.makeText(getActivity(), "Loading...", Toast.LENGTH_LONG).show();

		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				try {
					final MobileServiceList<Foods> result = MainActivity.mClient.getTable(Foods.class).execute().get();
					getActivity().runOnUiThread(new Runnable() {

						@Override
						public void run() {
							for (Foods item : result) {
								listings.add(item.Name);
								MainActivity.sDBFoodList.add(item);
							}
						}
					});
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				if (MainActivity.sDBFoodList.size() != 0) {
					((BaseAdapter) foodList.getAdapter()).notifyDataSetChanged();
					Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "No records found", Toast.LENGTH_SHORT).show();
				}
			}
		}.execute();
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
		Bundle bun = new Bundle();
		bun.putInt("Food", i);
		MainActivity.nextFragment(this, new FragmentFoodAdd(), bun, true, false);
	}
}
