package com.ameya.mymovieplan.model.request;

import lombok.Data;

@Data
public class UpdateScheduleRequestModel {
	
	private int id;
	private int theaterId;
	private int movieId;
	private int showtimeId;

}
