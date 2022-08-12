package com.ameya.mymovieplan.exception;

public enum ExceptionConstants {
	
	USER_NOT_FOUND("user.not.found"),
	GENERAL_EXCEPTION("general.exception"),
	NOMINATION_SUCCESS("nomination.success"),
	USER_ALREADY_EXISTS("user.already.exists"),
	NOMINATION_WINDOW_CLOSED("nomination.window.closed"),
	USER_UPDATE_SUCCESS("user.update.success"),
	ROLE_ALREADY_ASSINGED("user.role.assignment"),
	MATCH_UPDATE_SUCCESS("match.update.success"),
	NOMINATION_NOT_FOUND("no.such.nomination"),
	NOMINATION_ALREADY_EXISTS("nomination.already.exists"),
	MATCH_DELETE_SUCCESS("match.delete.success"),
	NOMINATION_UPDATE_SUCCESS("nomination.update.success"),
	USER_CREATION_FAILED("user.creation.failed"),
	USER_DELETE_SUCCESS("user.delete.success"),
	CITY_ALREADY_EXISTS("city.already.exists"),
	CITY_NOT_FOUND("city.not.found"),
	GENRE_ALREADY_EXISTS("genre.already.exists"),
	GENRE_NOT_FOUND("genre.not.found"),
	LANGUAGE_ALREADY_EXISTS("language.already.exists"),
	LANGUAGE_NOT_FOUND("language.not.found"),
	SHOWTIME_ALREADY_EXISTS("showtime.already.exists"),
	SHOWTIME_NOT_FOUND("showtime.not.found");
	
	private final String type;

	private ExceptionConstants(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return this.type;
	}
}
