package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.exception.city.CityAlreadyExistsException;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;

public interface CityService {
	
	CityDto addCity(CityDto city) throws CityAlreadyExistsException;
	
	CityDto getCityById(int id) throws NoSuchCityException;
	
	List<CityDto> getAllCities();

	CityDto updateCity(CityDto cityDto) throws NoSuchCityException;

	String deleteCity(int id) throws NoSuchCityException;

}
