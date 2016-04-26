package com.sweproject.calorietracker.ListViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sweproject.calorietracker.Callbacks.DeleteFoodListener;
import com.sweproject.calorietracker.DataObjects.Food_Day;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.R;

import java.util.ArrayList;

/**
 * Created by Marcus on 2/15/2016.
 */
public class AdapterDayFood extends BaseAdapter {

	private Context mContext;
	private ArrayList<Foods> mData;
	private ArrayList<ServingSizes> mDataServ;
	private ArrayList<Food_Day> mDataDay;
	private DeleteFoodListener mListener;

	public AdapterDayFood(Context context, ArrayList<Foods> data, ArrayList<ServingSizes> serv, ArrayList<Food_Day> day, DeleteFoodListener deleteFoodListener) {
		mContext = context;
		mData = new ArrayList<>();
		mDataServ = new ArrayList<>();
		mDataDay = new ArrayList<>();
		mData.addAll(data);
		mDataServ.addAll(serv);
		mDataDay.addAll(day);
		mListener = deleteFoodListener;
	}

	public void setData(ArrayList<Foods> data, ArrayList<ServingSizes> serv, ArrayList<Food_Day> day) {
		mData = data;
		mDataServ = serv;
		mDataDay = day;
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

		float cal = 0;
		float size = 1;

		for (ServingSizes s : mDataServ) {
			if (s.getFoodId().equals(mData.get(count).getId())) {
				cal = s.getCalories();
				for (Food_Day d : mDataDay) {
					if (d.getServingSizeId().equals(s.getId())) {
						size = d.getNumberOfServings();
						break;
					}
				}
				break;
			}
		}

		String total = String.valueOf(cal * size);
		((TextView) row.findViewById(R.id.single_row_title)).setText(mData.get(count).getName());
		((TextView) row.findViewById(R.id.single_row_calorie)).setText(total);

		ImageView icon = (ImageView) row.findViewById(R.id.single_row_ic);
		icon.setTag(count);
		icon.setOnClickListener(mListener);

		return row;
	}
}
