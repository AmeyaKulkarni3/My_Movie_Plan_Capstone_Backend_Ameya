package com.ameya.mymovieplan.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class MovieDto {

	private int id;
	private String name;
	private LocalDate releaseDate;
	private String directors;
	private String cast;
	private String poster;
	private List<TheaterDto> theaters;
	private List<GenreDto> genres;
	private List<LanguageDto> languages;

}
