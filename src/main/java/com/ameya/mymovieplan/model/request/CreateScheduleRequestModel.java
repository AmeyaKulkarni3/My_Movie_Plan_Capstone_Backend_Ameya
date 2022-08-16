package com.ameya.mymovieplan.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateScheduleRequestModel {
	
	private int theaterId;
	private int movieId;
	private int showtimeId;
	private LocalDate date;

}
