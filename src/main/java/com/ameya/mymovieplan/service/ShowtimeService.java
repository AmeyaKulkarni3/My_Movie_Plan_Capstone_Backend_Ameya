package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.exception.language.LanguageAlreadyExistsException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;

public interface ShowtimeService {
	
	ShowtimeDto addShowtime(ShowtimeDto dto) throws ShowtimeAlreadyExistsException;
	
	ShowtimeDto getShowtimeById(int id) throws NoSuchShowtimeException;
	
	List<ShowtimeDto> getAllShowtimes();
	
	ShowtimeDto updateShowtime(ShowtimeDto dto) throws NoSuchShowtimeException;
	
	String deleteShowtime(int id) throws NoSuchShowtimeException;

}
