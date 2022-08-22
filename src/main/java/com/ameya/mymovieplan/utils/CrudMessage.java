package com.ameya.mymovieplan.utils;

public enum CrudMessage {
	
	CITY_DELETE_SUCCESS("city.delete.success"),
	THEATER_DELETE_SUCCESS("theater.delete.success"),
	SCHEDULE_DELETE_SUCCESS("schedule.delete.success"),
	MOVIE_DELETE_SUCCESS("movie.delete.success");
	
	private final String type;

	private CrudMessage(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type;
	}

}
