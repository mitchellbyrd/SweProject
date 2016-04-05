package com.sweproject.calorietracker.DataObjects;

/**
 * Created by Marcus on 3/17/2016.
 */
public class Foods {
	private String id;
	private String Name;
	private String UserId;
	private String UsdaId;

	public Foods(String name, String userId) {
		this.Name = name;
		this.UserId = userId;
	}

	//region Setters

	public void setName(String name) {
		Name = name;
	}

	public void setUsdaId(String usdaId) {
		UsdaId = usdaId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	//endregion

	//region Getters

	public String getId() {
		return id;
	}

	public String getName() {
		return Name;
	}

	public String getUsdaId() {
		return UsdaId;
	}

	public String getUserId() {
		return UserId;
	}

	//endregion
}
