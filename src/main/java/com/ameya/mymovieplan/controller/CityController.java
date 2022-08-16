package com.ameya.mymovieplan.controller;

import java.util.List;

import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.exception.city.CityAlreadyExistsException;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;
import com.ameya.mymovieplan.service.CityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	CityService cityService;
	
	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<CityDto> addCity(@RequestBody CityDto cityDto) throws CityAlreadyExistsException{
		
		return ResponseEntity.ok(cityService.addCity(cityDto));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CityDto> getCityByName(@PathVariable int id) throws NoSuchCityException{
		
		return ResponseEntity.ok(cityService.getCityById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<CityDto>> getAllCities(){
		
		return ResponseEntity.ok(cityService.getAllCities());
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping
	public ResponseEntity<CityDto> updateCity(@RequestBody CityDto cityDto) throws NoSuchCityException{
		return ResponseEntity.ok(cityService.updateCity(cityDto));
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCity(@PathVariable int id) throws NoSuchCityException{
		return ResponseEntity.ok(cityService.deleteCity(id));
	}
	
	

}
