package com.sweproject.calorietracker.Callbacks;

import java.util.ArrayList;

/**
 * Created by Marcus on 3/28/2016.
 */
public interface DBDataListener {

	/**
	 * When a call to the getDBData is made, when valid data is received, this method is called
	 **/
	void onGoodDataReturn(ArrayList<Object> data);

	/**
	 * When a call to the getDBData is made, when invalid data is received, this method is called
	 **/
	void onBadDataReturn(Exception exception);
}
