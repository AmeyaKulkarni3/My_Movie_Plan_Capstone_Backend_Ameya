package com.ameya.mymovieplan.model.request;

import java.util.List;

import lombok.Data;

@Data
public class CreateBookingRequestModel {
	
	private int scheduleId;
	private List<Integer> seatNumbers;
	private String userId;

}
