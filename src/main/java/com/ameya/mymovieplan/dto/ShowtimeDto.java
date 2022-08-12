package com.ameya.mymovieplan.dto;

import java.util.List;

import lombok.Data;

@Data
public class ShowtimeDto {

	private int id;
	private String time;
	private List<TheaterDto> theaters;

}
