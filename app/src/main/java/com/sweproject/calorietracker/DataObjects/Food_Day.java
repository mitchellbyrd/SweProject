package com.sweproject.calorietracker.DataObjects;

/**
 * Created by Marcus on 3/17/2016.
 */
public class Food_Day {
	private String id;
	private float NumberOfServings;
	private String ServingSizeId;
	private String DayId;

	public Food_Day(String dayId, float numberOfServings, String servingSizeId) {
		DayId = dayId;
		NumberOfServings = numberOfServings;
		ServingSizeId = servingSizeId;
	}

	//region Setters
	public void setDayId(String dayId) {
		DayId = dayId;
	}

	public void setNumberOfServings(float numberOfServings) {
		NumberOfServings = numberOfServings;
	}

	public void setServingSizeId(String servingSizeId) {
		ServingSizeId = servingSizeId;
	}
	//endregion

	//region Getters
	public String getDayId() {
		return DayId;
	}

	public String getId() {
		return id;
	}

	public float getNumberOfServings() {
		return NumberOfServings;
	}

	public String getServingSizeId() {
		return ServingSizeId;
	}
	//endregion
}
