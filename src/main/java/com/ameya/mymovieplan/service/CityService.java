package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.exception.city.CityAlreadyExistsException;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;
import com.ameya.mymovieplan.utils.OutputMessage;

public interface CityService {
	
	CityDto addCity(CityDto city) throws CityAlreadyExistsException;
	
	CityDto getCityById(int id) throws NoSuchCityException;
	
	List<CityDto> getAllCities();

	CityDto updateCity(CityDto cityDto) throws NoSuchCityException;

	OutputMessage deleteCity(int id) throws NoSuchCityException;

}
