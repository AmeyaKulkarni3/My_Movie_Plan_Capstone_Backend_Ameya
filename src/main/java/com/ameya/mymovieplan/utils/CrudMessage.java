package com.ameya.mymovieplan.utils;

public enum CrudMessage {
	
	CITY_DELETE_SUCCESS("city.delete.success"),
	THEATER_DELETE_SUCCESS("theater.delete.success");
	
	private final String type;

	private CrudMessage(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type;
	}

}
