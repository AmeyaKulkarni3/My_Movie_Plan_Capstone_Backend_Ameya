package com.ameya.mymovieplan.dto;

import java.util.List;

import lombok.Data;

@Data
public class TierDto {

	private int id;
	private String name;
	private int seats;
	private double price;
	private List<TheaterDto> theaters;
	private int priority;

}
