package com.sweproject.calorietracker.ListViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweproject.calorietracker.Callbacks.DeleteFoodListener;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.R;

import java.util.ArrayList;

/**
 * Created by Marcus on 2/15/2016.
 */
public class AdapterDayFood extends BaseAdapter {

	private Context mContext;
	private ArrayList<Foods> mData;
	private DeleteFoodListener mListener;

	public AdapterDayFood(Context context, ArrayList<Foods> data, DeleteFoodListener deleteFoodListener) {
		mContext = context;
		mData = new ArrayList<>();
		mData.addAll(data);
		mListener = deleteFoodListener;
	}

	public void setData(ArrayList<Foods> data) {
		mData = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int i) {
		return mData.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int count, View oldView, ViewGroup parent) {

		View row = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.calendar_day_single_row, parent, false);

		((TextView) row.findViewById(R.id.single_row_title)).setText(mData.get(count).getName());
		((TextView) row.findViewById(R.id.single_row_calorie)).setText("" + 0);

		ImageView icon = (ImageView) row.findViewById(R.id.single_row_ic);
		icon.setTag(count);
		icon.setOnClickListener(mListener);

		return row;
	}
}
