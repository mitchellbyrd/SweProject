package com.sweproject.calorietracker.DataObjects;

/**
 * Created by Marcus on 3/17/2016.
 */
public class ServingSizes {
	private float id;
	private float Amount;
	private float Calories;
	private float Carbs;
	private float Proteins;
	private float Fats;
	private String ServingSizeType;
	private String FoodId;

	public ServingSizes(float calories, float carbs, float proteins, float fats, String type) {
		this.Calories = calories;
		this.Carbs = carbs;
		this.Proteins = proteins;
		this.Fats = fats;
		this.ServingSizeType = type;
	}

	//region Setters
	public void setAmount(float amount) {
		Amount = amount;
	}

	public void setCalories(float calories) {
		Calories = calories;
	}

	public void setCarbs(float carbs) {
		Carbs = carbs;
	}

	public void setFats(float fats) {
		Fats = fats;
	}

	public void setFoodId(String foodId) {
		FoodId = foodId;
	}

	public void setProteins(float proteins) {
		Proteins = proteins;
	}

	public void setServingSizeType(String servingSizeType) {
		ServingSizeType = servingSizeType;
	}
	//endregion Setters

	//region Getters
	public float getAmount() {
		return Amount;
	}

	public float getCalories() {
		return Calories;
	}

	public float getCarbs() {
		return Carbs;
	}

	public float getFats() {
		return Fats;
	}

	public String getFoodId() {
		return FoodId;
	}

	public float getId() {
		return id;
	}

	public float getProteins() {
		return Proteins;
	}

	public String getServingSizeType() {
		return ServingSizeType;
	}
	//endregion Getters
}
