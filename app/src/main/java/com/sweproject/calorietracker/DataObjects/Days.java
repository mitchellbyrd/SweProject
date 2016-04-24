package com.sweproject.calorietracker.DataObjects;

/**
 * Created by Marcus on 3/17/2016.
 */
public class Days {
	private String id;
	private String Date;
	private float CurrentTotalCalorie;
	private float CurrentTotalFat;
	private float CurrentTotalCarb;
	private float CurrentTotalProtein;
	private float GoalTotalCalorie;
	private float GoalTotalFat;
	private float GoalTotalCarb;
	private float GoalTotalProtein;
	private String UserId;

	public Days(String date, String userId) {
		this.Date = date;
		this.UserId = userId;
	}

	//region Setter

	public void setCurrentTotalCalorie(float currentTotalCalorie) {
		CurrentTotalCalorie = currentTotalCalorie;
	}

	public void setCurrentTotalCarb(float currentTotalCarb) {
		CurrentTotalCarb = currentTotalCarb;
	}

	public void setCurrentTotalFat(float currentTotalFat) {
		CurrentTotalFat = currentTotalFat;
	}

	public void setCurrentTotalProtein(float currentTotalProtein) {
		CurrentTotalProtein = currentTotalProtein;
	}

	/**
	 * If this class will be sent to an Azure db, pass the date formatted by the SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	 */
	public void setDate(String date) {
		Date = date;
	}

	public void setGoalTotalCalorie(float goalTotalCalorie) {
		GoalTotalCalorie = goalTotalCalorie;
	}

	public void setGoalTotalCarb(float goalTotalCarb) {
		GoalTotalCarb = goalTotalCarb;
	}

	public void setGoalTotalFat(float goalTotalFat) {
		GoalTotalFat = goalTotalFat;
	}

	public void setGoalTotalProtein(float goalTotalProtein) {
		GoalTotalProtein = goalTotalProtein;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	//endregion

	//region Getter

	public float getCurrentTotalCalorie() {
		return CurrentTotalCalorie;
	}

	public float getCurrentTotalCarb() {
		return CurrentTotalCarb;
	}

	public float getCurrentTotalFat() {
		return CurrentTotalFat;
	}

	public float getCurrentTotalProtein() {
		return CurrentTotalProtein;
	}

	public String getDate() {
		return Date.split("T")[0];
	}

	public float getGoalTotalCalorie() {
		return GoalTotalCalorie;
	}

	public float getGoalTotalCarb() {
		return GoalTotalCarb;
	}

	public float getGoalTotalFat() {
		return GoalTotalFat;
	}

	public float getGoalTotalProtein() {
		return GoalTotalProtein;
	}

	public String getId() {
		return id;
	}

	public String getUserId() {
		return UserId;
	}

	//endregion
}
