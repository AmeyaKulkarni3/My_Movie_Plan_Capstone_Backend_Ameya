package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.utils.OutputMessage;

public interface GenreService {
	
	GenreDto addGenre(GenreDto dto) throws GenreAlreadyExistsException;
	
	GenreDto getGenreById(int id) throws NoSuchGenreException;
	
	List<GenreDto> getAllGenres();
	
	GenreDto updateGenre(GenreDto dto) throws NoSuchGenreException;
	
	OutputMessage deleteGenre(int id) throws NoSuchGenreException;

}
