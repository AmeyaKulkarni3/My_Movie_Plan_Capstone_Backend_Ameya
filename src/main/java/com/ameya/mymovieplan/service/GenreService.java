package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;

public interface GenreService {
	
	GenreDto addGenre(GenreDto dto) throws GenreAlreadyExistsException;
	
	GenreDto getGenreById(int id) throws NoSuchGenreException;
	
	List<GenreDto> getAllGenres();
	
	GenreDto updateGenre(GenreDto dto) throws NoSuchGenreException;
	
	String deleteGenre(int id) throws NoSuchGenreException;

}
