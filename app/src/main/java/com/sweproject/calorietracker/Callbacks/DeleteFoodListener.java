package com.sweproject.calorietracker.Callbacks;

import android.view.View;
import android.widget.BaseAdapter;

import com.sweproject.calorietracker.FragmentDay;

/**
 * Created by Marcus on 3/3/2016.
 */
public class DeleteFoodListener implements View.OnClickListener {

	@Override
	public void onClick(View view) {
		FragmentDay.sAddedFoodList.remove((int) view.getTag());
		((BaseAdapter) FragmentDay.sFoodList.getAdapter()).notifyDataSetChanged();
	}
}
