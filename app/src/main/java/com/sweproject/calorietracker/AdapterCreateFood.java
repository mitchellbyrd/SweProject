package com.sweproject.calorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Marcus on 3/24/2016.
 */
public class AdapterCreateFood extends BaseAdapter {

	private Context mContext;
	private int mCount;
	private View.OnClickListener mListener;

	public AdapterCreateFood(Context context, View.OnClickListener listner) {
		mContext = context;
		mCount = 1;
		mListener = listner;
	}

	@Override
	public int getCount() {
		return mCount;
	}

	public void setCount(int i) {
		mCount = i;
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int count, View oldView, ViewGroup parent) {

		View row = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.create_food_single_row, parent, false);

		((TextView) row.findViewById(R.id.create_food_single_calories_edit)).setText("100");
		((TextView) row.findViewById(R.id.create_food_single_protein_edit)).setText("100");
		((TextView) row.findViewById(R.id.create_food_single_carbs_edit)).setText("100");
		((TextView) row.findViewById(R.id.create_food_single_fats_edit)).setText("100");
		row.findViewById(R.id.create_food_single_fab).setOnClickListener(mListener);

		return row;
	}
}
