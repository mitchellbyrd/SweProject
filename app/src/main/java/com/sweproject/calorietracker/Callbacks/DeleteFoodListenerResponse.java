package com.sweproject.calorietracker.Callbacks;

import com.sweproject.calorietracker.DataObjects.Food_Day;
import com.sweproject.calorietracker.DataObjects.ServingSizes;

/**
 * Created by Marcus on 4/13/2016.
 */
public interface DeleteFoodListenerResponse {

	void onFoodDeleted(ServingSizes s, Food_Day d);
}
