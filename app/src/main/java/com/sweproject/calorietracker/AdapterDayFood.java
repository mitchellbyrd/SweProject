package com.sweproject.calorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Marcus on 2/15/2016.
 */
public class AdapterDayFood extends BaseAdapter {

	private Context mContext;
	private DeleteFoodListener mListener;

	public AdapterDayFood(Context context, DeleteFoodListener deleteFoodListener) {
		mContext = context;
		mListener = deleteFoodListener;
	}

	@Override
	public int getCount() {
		return MainActivity.foodList.size();
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
				.inflate(R.layout.calendar_day_single_row, parent, false);

		((TextView) row.findViewById(R.id.single_row_title)).setText("Food item " + MainActivity.foodList.get(count));
		((TextView) row.findViewById(R.id.single_row_calorie)).setText("" + MainActivity.foodList.get(count));

		ImageView icon = (ImageView) row.findViewById(R.id.single_row_ic);
		icon.setTag(count);
		icon.setOnClickListener(mListener);

		return row;
	}
}