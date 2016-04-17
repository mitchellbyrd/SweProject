package com.sweproject.calorietracker.ListViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.R;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/24/2016.
 */
public class AdapterCreateFood extends BaseAdapter {

	private Context mContext;
	private ArrayList<ServingSizes> mServingSizes;

	public AdapterCreateFood(Context context, ArrayList<ServingSizes> servingSizes) {
		mContext = context;
		mServingSizes = servingSizes;
	}

	@Override
	public int getCount() {
		return mServingSizes.size();
	}

	@Override
	public Object getItem(int i) {
		return mServingSizes.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View oldView, ViewGroup parent) {

		View row = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.create_food_single_row, parent, false);

		String cal = String.valueOf(mServingSizes.get(i).getCalories());
		String pro = String.valueOf(mServingSizes.get(i).getProteins());
		String car = String.valueOf(mServingSizes.get(i).getCarbs());
		String fat = String.valueOf(mServingSizes.get(i).getFats());
		String type = mServingSizes.get(i).getServingSizeType();

		((TextView) row.findViewById(R.id.create_food_single_calories_edit)).setText(cal);
		((TextView) row.findViewById(R.id.create_food_single_protein_edit)).setText(pro);
		((TextView) row.findViewById(R.id.create_food_single_carbs_edit)).setText(car);
		((TextView) row.findViewById(R.id.create_food_single_fats_edit)).setText(fat);
		((TextView) row.findViewById(R.id.create_food_single_serving_type_edit)).setText(type);

		return row;
	}
}
