package com.sweproject.calorietracker.Callbacks;

import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import com.sweproject.calorietracker.DataObjects.Food_Day;
import com.sweproject.calorietracker.DataObjects.Foods;
import com.sweproject.calorietracker.DataObjects.ServingSizes;
import com.sweproject.calorietracker.FragmentCalendar;
import com.sweproject.calorietracker.FragmentDay;
import com.sweproject.calorietracker.MainActivity;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/3/2016.
 */
public class DeleteFoodListener implements View.OnClickListener, DBDataListener {

	private Foods mFood;
	private ArrayList<Food_Day> mFoodDayList;
	private ArrayList<ServingSizes> mServingList;
	private DeleteFoodListenerResponse mResponse;

	public DeleteFoodListener(DeleteFoodListenerResponse response) {
		mResponse = response;
	}

	@Override
	public void onClick(View view) {

		mFoodDayList = new ArrayList<>();
		mServingList = new ArrayList<>();

		mFood = FragmentDay.sAddedFoodList.get((int) view.getTag());
		FragmentDay.sAddedFoodList.remove((int) view.getTag());
		((BaseAdapter) FragmentDay.sFoodList.getAdapter()).notifyDataSetChanged();

		Log.d("Azure", "getting food days");
		// get all food_days for currentDay
		MainActivity.getDBData(Food_Day.class, this, "DayId", FragmentCalendar.currentDay.getId());
	}

	@Override
	public void onGoodDataReturn(ArrayList<Object> data) {
		if (!data.isEmpty()) {
			if (data.get(0) instanceof Food_Day) {
				for (Object o : data) {
					mFoodDayList.add((Food_Day) o);
				}
				Log.d("Azure", "all food days");
				// get all servings_sizes for selected food
				MainActivity.getDBData(ServingSizes.class, this, "FoodId", mFood.getId());
			} else if (data.get(0) instanceof ServingSizes) {
				for (Object o : data) {
					mServingList.add((ServingSizes) o);
				}
				Log.d("Azure", "All serving");
				deleteFoodItem();
			}
		}
	}

	private void deleteFoodItem() {
		// compare each serving id to each food_day; if match delete that food_day
		for (Food_Day d : mFoodDayList) {
			for (ServingSizes s : mServingList) {
				Log.d("Azure", "Checking to delete");
				if (d.getServingSizeId().equals(s.getId())) {
					Log.d("Azure", "Delete");
					mResponse.onFoodDeleted(s);
					MainActivity.deleteDBData(Food_Day.class, d);
					return;
				}
			}
		}
	}

	@Override
	public void onBadDataReturn(Exception exception) {
		exception.printStackTrace();
	}

	@Override
	public void onGoodInsert() { /* ignore */ }

	@Override
	public void onGoodInsertReturn(Object obj) { /* ignore */ }
}