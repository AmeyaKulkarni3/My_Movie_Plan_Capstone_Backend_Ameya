package com.ameya.mymovieplan.dto;

import java.util.List;

import lombok.Data;

@Data
public class BookingDto {

	private int id;
	private ScheduleDto schedule;
	private List<SeatDto> seats;
	private UserDto user;
	private double totalPrice;

}
