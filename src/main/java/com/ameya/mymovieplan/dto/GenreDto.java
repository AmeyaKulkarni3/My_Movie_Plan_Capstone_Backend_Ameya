package com.ameya.mymovieplan.dto;

import java.util.List;

import lombok.Data;

@Data
public class GenreDto {

	private int id;
	private String name;
	private List<MovieDto> movies;

}
