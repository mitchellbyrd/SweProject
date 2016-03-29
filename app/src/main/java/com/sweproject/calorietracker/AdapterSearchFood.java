package com.sweproject.calorietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sweproject.calorietracker.DataObjects.Foods;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/3/2016.
 */
public class AdapterSearchFood extends BaseAdapter {

	private Context mContext;
	private ArrayList<Foods> mData;

	public AdapterSearchFood(Context context, ArrayList<Foods> data) {
		mContext = context;
		mData = data;
	}

	public void setData(ArrayList<Object> data) {
		ArrayList<Foods> temp = new ArrayList<>();

		for (Object o : data) {
			temp.add((Foods) o);
		}
		mData = temp;
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
		return -1;
	}

	@Override
	public View getView(int count, View oldView, ViewGroup parent) {
		View row = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.search_food_single_row, parent, false);

		((TextView) row.findViewById(R.id.search_food_title)).setText(mData.get(count).Name);

		return row;
	}
}
