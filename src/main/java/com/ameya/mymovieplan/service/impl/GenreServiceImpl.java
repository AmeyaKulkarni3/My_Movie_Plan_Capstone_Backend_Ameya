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
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.repository.GenreRepository;
import com.ameya.mymovieplan.service.GenreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("GenreValidationMessages.properties")
@Transactional
public class GenreServiceImpl implements GenreService {

	@Autowired
	GenreRepository genreRepository;

	@Autowired
	Environment env;

	@Override
	public GenreDto addGenre(GenreDto dto) throws GenreAlreadyExistsException {

		Genre genre = genreRepository.findByName(dto.getName());

		if (genre != null) {
			throw new GenreAlreadyExistsException(env.getProperty(ExceptionConstants.GENRE_ALREADY_EXISTS.toString()));
		}

		Genre g = new Genre();

		g.setName(dto.getName());

		Genre savedGenre = genreRepository.save(g);

		GenreDto returnValue = new GenreDto();
		returnValue.setId(savedGenre.getId());
		returnValue.setName(savedGenre.getName());

		return returnValue;
	}
	
	private GenreDto dataTransfer(Genre genre) {
		
		GenreDto dto = new GenreDto();
		
		dto.setId(genre.getId());
		dto.setName(genre.getName());
		
		List<Movie> movies = genre.getMovies();
		List<MovieDto> movieDtos = new ArrayList<>();
		for(Movie m : movies) {
			MovieDto mdto = new MovieDto();
			mdto.setId(m.getId());
			mdto.setName(m.getName());
			mdto.setDirectors(m.getDirectors());
			mdto.setCast(m.getCast());
			mdto.setPoster(m.getPoster());
			mdto.setReleaseDate(m.getReleaseDate());
			List<Theater> theaters = m.getTheaters();
			List<TheaterDto> theaterDtos = new ArrayList<>();
			for(Theater t : theaters) {
				TheaterDto tdto = new TheaterDto();
				tdto.setId(t.getId());
				tdto.setName(t.getName());
				List<Showtime> showtimes = t.getShowtimes();
				List<ShowtimeDto> showtimeDtos = new ArrayList<>();
				for(Showtime s : showtimes) {
					ShowtimeDto sdto = new ShowtimeDto();
					sdto.setId(s.getId());
					sdto.setTime(s.getTime());
					showtimeDtos.add(sdto);
				}
				tdto.setShowtimes(showtimeDtos);
				Address a = t.getAddress();
				AddressDto adto = new AddressDto();
				adto.setId(a.getId());
				adto.setLine1(a.getLine1());
				adto.setLine2(a.getLine2());
				adto.setPincode(a.getPincode());
				City c = a.getCity();
				CityDto cdto = new CityDto();
				cdto.setId(c.getId());
				cdto.setName(c.getName());
				adto.setCityDto(cdto);
				tdto.setAddress(adto);
				tdto.setCity(cdto);
				theaterDtos.add(tdto);
			}
			
			List<Language> languages = m.getLanguages();
			List<LanguageDto> languageDtos = new ArrayList<>();
			for(Language l : languages) {
				LanguageDto ldto = new LanguageDto();
				ldto.setId(l.getId());
				ldto.setName(l.getName());
				languageDtos.add(ldto);
			}
			mdto.setLanguages(languageDtos);
			movieDtos.add(mdto);
		}
		dto.setMovies(movieDtos);
		
		return dto;
		
	}

	@Override
	public GenreDto getGenreById(int id) throws NoSuchGenreException {

		Genre genre = genreRepository.findById(id).orElseThrow(
				() -> new NoSuchGenreException(env.getProperty(ExceptionConstants.GENRE_NOT_FOUND.toString())));
		
		GenreDto dto = dataTransfer(genre);
		
		return dto;
	}

	@Override
	public List<GenreDto> getAllGenres() {
		
		List<Genre> genres = (List<Genre>) genreRepository.findAll();
		List<GenreDto> dtos = new ArrayList<>();
		for(Genre g : genres) {
			GenreDto gdto = dataTransfer(g);
			dtos.add(gdto);
		}
		return dtos;
	}

	@Override
	public GenreDto updateGenre(GenreDto dto) throws NoSuchGenreException {
		Genre genre = genreRepository.findById(dto.getId()).orElseThrow(
				() -> new NoSuchGenreException(env.getProperty(ExceptionConstants.GENRE_NOT_FOUND.toString())));
		
		if(genre.getName() != null && !genre.getName().equals(dto.getName())) {
			genre.setName(dto.getName());
		}
		
		Genre saved = genreRepository.save(genre);
		
		GenreDto gdto = dataTransfer(saved);
		return gdto;
	}

	@Override
	public String deleteGenre(int id) throws NoSuchGenreException {
		
		Genre genre = genreRepository.findById(id).orElseThrow(
				() -> new NoSuchGenreException(env.getProperty(ExceptionConstants.GENRE_NOT_FOUND.toString())));
		
		genreRepository.delete(genre);
		
		return "Genre Deleted Successfully!";
	}

}
