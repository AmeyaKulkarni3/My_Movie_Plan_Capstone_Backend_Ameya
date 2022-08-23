package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.exception.language.LanguageAlreadyExistsException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.utils.OutputMessage;

public interface LanguageService {
	
	LanguageDto addLanguage(LanguageDto dto) throws LanguageAlreadyExistsException;
	
	LanguageDto getLanguageById(int id) throws NoSuchLanguageException;
	
	List<LanguageDto> getAllLanguages();
	
	LanguageDto updateLanguage(LanguageDto dto) throws NoSuchLanguageException;
	
	OutputMessage deleteLanguage(int id) throws NoSuchLanguageException;

}
