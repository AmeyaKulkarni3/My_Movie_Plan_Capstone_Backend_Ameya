package com.ameya.mymovieplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ameya.mymovieplan.dto.AddressDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.entity.Address;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Genre;
import com.ameya.mymovieplan.entity.Language;
import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.entity.Showtime;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.language.LanguageAlreadyExistsException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.repository.LanguageRepository;
import com.ameya.mymovieplan.service.LanguageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("LanguageValidationMessages.properties")
@Transactional
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	LanguageRepository languageRepository;

	@Autowired
	Environment env;

	@Override
	public LanguageDto addLanguage(LanguageDto dto) throws LanguageAlreadyExistsException {

		Language language = languageRepository.findByName(dto.getName());
		if (language != null) {
			throw new LanguageAlreadyExistsException(
					env.getProperty(ExceptionConstants.LANGUAGE_ALREADY_EXISTS.toString()));
		}

		Language l = new Language();
		l.setName(dto.getName());

		Language saved = languageRepository.save(l);

		LanguageDto ldto = new LanguageDto();

		ldto.setId(saved.getId());
		ldto.setName(saved.getName());

		return ldto;
	}

	private LanguageDto dataTransfer(Language language) {
		LanguageDto dto = new LanguageDto();
		dto.setId(language.getId());
		dto.setName(language.getName());
		List<Movie> movies = language.getMovies();
		List<MovieDto> movieDtos = new ArrayList<>();
		for (Movie m : movies) {
			MovieDto mdto = new MovieDto();
			mdto.setId(m.getId());
			mdto.setName(m.getName());
			mdto.setDirectors(m.getDirectors());
			mdto.setCast(m.getCast());
			mdto.setPoster(m.getPoster());
			mdto.setReleaseDate(m.getReleaseDate());
			List<Genre> genres = m.getGenres();
			List<GenreDto> genreDtos = new ArrayList<>();
			for (Genre g : genres) {
				GenreDto gdto = new GenreDto();
				gdto.setId(g.getId());
				gdto.setName(g.getName());
				genreDtos.add(gdto);
			}
			mdto.setGenres(genreDtos);
			List<Theater> theaters = m.getTheaters();
			List<TheaterDto> theaterDtos = new ArrayList<>();
			for (Theater t : theaters) {
				TheaterDto tdto = new TheaterDto();
				tdto.setId(t.getId());
				tdto.setName(t.getName());
				List<Showtime> showtimes = t.getShowtimes();
				List<ShowtimeDto> showtimeDtos = new ArrayList<>();
				for (Showtime s : showtimes) {
					ShowtimeDto sdto = new ShowtimeDto();
					sdto.setId(s.getId());
					sdto.setTime(s.getTime());
					showtimeDtos.add(sdto);
				}
				tdto.setShowtimes(showtimeDtos);
				Address a = t.getAddress();
				AddressDto adto = new AddressDto();
				City c = t.getCity();
				CityDto cdto = new CityDto();
				cdto.setId(c.getId());
				cdto.setName(c.getName());
				adto.setId(a.getId());
				adto.setLine1(a.getLine1());
				adto.setLine2(a.getLine2());
				adto.setPincode(a.getPincode());
				adto.setCityDto(cdto);
				tdto.setAddress(adto);
				theaterDtos.add(tdto);
			}
			mdto.setTheaters(theaterDtos);
			movieDtos.add(mdto);
		}
		dto.setMovies(movieDtos);
		return dto;

	}

	@Override
	public LanguageDto getLanguageById(int id) throws NoSuchLanguageException {
		Language language = languageRepository.findById(id).orElseThrow(
				() -> new NoSuchLanguageException(env.getProperty(ExceptionConstants.LANGUAGE_NOT_FOUND.toString())));
		LanguageDto dto = dataTransfer(language);
		return dto;
	}

	@Override
	public List<LanguageDto> getAllLanguages() {
		List<Language> languages = (List<Language>) languageRepository.findAll();
		List<LanguageDto> dtos = new ArrayList<>();
		for (Language l : languages) {
			LanguageDto ldto = dataTransfer(l);
			dtos.add(ldto);
		}
		return dtos;
	}

	@Override
	public LanguageDto updateLanguage(LanguageDto dto) throws NoSuchLanguageException {
		Language language = languageRepository.findById(dto.getId()).orElseThrow(
				() -> new NoSuchLanguageException(env.getProperty(ExceptionConstants.LANGUAGE_NOT_FOUND.toString())));
		if(dto.getName() != null && !dto.getName().equals(language.getName())) {
			language.setName(dto.getName());
		}
		
		Language updated = languageRepository.save(language);
		
		LanguageDto returnValue = dataTransfer(updated);
		
		return returnValue;
	}

	@Override
	public String deleteLanguage(int id) throws NoSuchLanguageException {
		Language language = languageRepository.findById(id).orElseThrow(
				() -> new NoSuchLanguageException(env.getProperty(ExceptionConstants.LANGUAGE_NOT_FOUND.toString())));
		languageRepository.delete(language);
		return "Language Deleted Successfully!";
	}

}
