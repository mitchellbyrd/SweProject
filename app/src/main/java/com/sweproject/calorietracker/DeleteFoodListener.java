package com.sweproject.calorietracker;

import android.view.View;
import android.widget.BaseAdapter;

/**
 * Created by Marcus on 3/3/2016.
 */
public class DeleteFoodListener implements View.OnClickListener {

	@Override
	public void onClick(View view) {
		MainActivity.sAddedFoodList.remove((int) view.getTag());
		((BaseAdapter) FragmentDay.foodList.getAdapter()).notifyDataSetChanged();
	}
}
