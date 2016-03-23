package com.sweproject.calorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Marcus on 3/3/2016.
 */
public class AdapterSearchFood extends BaseAdapter {

	private Context mContext;

	public AdapterSearchFood(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return 0;
	}

	@Override
	public Object getItem(int i) {
		return i;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int count, View oldView, ViewGroup parent) {
		View row = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.search_food_single_row, parent, false);

		((TextView) row.findViewById(R.id.search_food_title)).setText("" + 0);

		return row;
	}
}
