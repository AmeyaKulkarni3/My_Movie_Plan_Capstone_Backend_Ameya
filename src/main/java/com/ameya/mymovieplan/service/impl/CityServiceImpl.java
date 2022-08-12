package com.ameya.mymovieplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ameya.mymovieplan.dto.AddressDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.entity.Address;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Genre;
import com.ameya.mymovieplan.entity.Language;
import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.city.CityAlreadyExistsException;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;
import com.ameya.mymovieplan.repository.CityRepository;
import com.ameya.mymovieplan.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:CityValidationMessages.properties")
@Transactional
public class CityServiceImpl implements CityService {

	@Autowired
	CityRepository cityRepository;

	@Autowired
	Environment env;

	@Override
	public CityDto addCity(CityDto city) throws CityAlreadyExistsException {

		City cityEntity = new City();

		City c = cityRepository.findByName(city.getName());

		if (c != null) {
			throw new CityAlreadyExistsException(env.getProperty(ExceptionConstants.CITY_ALREADY_EXISTS.toString()));
		}

		cityEntity.setName(city.getName());

		City savedCity = cityRepository.save(cityEntity);

		CityDto returnValue = new CityDto();
		returnValue.setId(savedCity.getId());
		returnValue.setName(savedCity.getName());

		return returnValue;
	}

	@Override
	public CityDto getCityById(int id) throws NoSuchCityException {

		City c = cityRepository.findById(id).orElseThrow(
				() -> new NoSuchCityException(env.getProperty(ExceptionConstants.CITY_NOT_FOUND.toString())));

		CityDto dto = dataTransfer(c);

		return dto;
	}

	@Override
	public List<CityDto> getAllCities() {

		List<City> cities = (List<City>) cityRepository.findAll();
		
		List<CityDto> cityDtos = new ArrayList<>();
		for(City c : cities) {
			CityDto dto = dataTransfer(c);
			cityDtos.add(dto);
		}
		return cityDtos;
	}
	
	@Override
	public CityDto updateCity(CityDto cityDto) throws NoSuchCityException {
		
		City c = cityRepository.findById(cityDto.getId()).orElseThrow(
				() -> new NoSuchCityException(env.getProperty(ExceptionConstants.CITY_NOT_FOUND.toString())));
		
		if(cityDto.getName() != null && !cityDto.getName().equals(c.getName())) {
			c.setName(cityDto.getName());
		}
		
		cityRepository.save(c);
		
		CityDto dto = dataTransfer(c);
		
		return dto;
	}
	
	@Override
	public String deleteCity(int id) throws NoSuchCityException {
		
		City c = cityRepository.findById(id).orElseThrow(
				() -> new NoSuchCityException(env.getProperty(ExceptionConstants.CITY_NOT_FOUND.toString())));
		
		cityRepository.delete(c);
		
		return "City Deleted Successfully";
	}

	private CityDto dataTransfer(City c) {

		CityDto dto = new CityDto();

		dto.setId(c.getId());
		dto.setName(c.getName());

		List<AddressDto> addresses = new ArrayList<>();
		for (Address ad : c.getAddresses()) {
			AddressDto adto = new AddressDto();
			adto.setId(ad.getId());
			adto.setLine1(ad.getLine1());
			adto.setLine2(ad.getLine2());
			adto.setPincode(ad.getPincode());
			addresses.add(adto);
		}
		dto.setAddresses(addresses);

		List<TheaterDto> theaters = new ArrayList<>();
		for (Theater t : c.getTheatres()) {
			TheaterDto tdto = new TheaterDto();
			tdto.setId(t.getId());
			tdto.setName(t.getName());
			List<MovieDto> movies = new ArrayList<>();
			for (Movie m : t.getMovies()) {
				MovieDto mdto = new MovieDto();
				mdto.setId(m.getId());
				mdto.setName(m.getName());
				mdto.setDirectors(m.getDirectors());
				mdto.setCast(m.getCast());
				mdto.setPoster(m.getPoster());
				mdto.setReleaseDate(m.getReleaseDate());
				List<GenreDto> genres = new ArrayList<>();
				for (Genre g : m.getGenres()) {
					GenreDto gdto = new GenreDto();
					gdto.setId(g.getId());
					gdto.setName(g.getName());
					genres.add(gdto);
				}
				mdto.setGenres(genres);
				List<LanguageDto> languages = new ArrayList<>();
				for (Language l : m.getLanguages()) {
					LanguageDto ldto = new LanguageDto();
					ldto.setId(l.getId());
					ldto.setName(l.getName());
					languages.add(ldto);
				}
				mdto.setLanguages(languages);
				movies.add(mdto);
			}
			tdto.setMovies(movies);
			theaters.add(tdto);
		}
		dto.setTheatres(theaters);

		return dto;

	}

	

	

}