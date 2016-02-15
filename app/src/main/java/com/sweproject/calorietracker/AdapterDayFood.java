package com.sweproject.calorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Marcus on 2/15/2016.
 */
public class AdapterDayFood extends BaseAdapter {

	private Context mContext;

	public AdapterDayFood(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return 8;
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

		TextView title = (TextView) row.findViewById(R.id.single_row_title);
		TextView calorie = (TextView) row.findViewById(R.id.single_row_calorie);

		title.setText("Food item " + count);
		calorie.setText("" + count);

		return row;
	}
}
