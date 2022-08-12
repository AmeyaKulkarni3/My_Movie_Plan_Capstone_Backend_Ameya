package com.ameya.mymovieplan.dto;

import java.util.List;

import com.ameya.mymovieplan.entity.City;

import lombok.Data;

@Data
public class TheaterDto {

	private int id;
	private String name;
	private CityDto city;
	private List<ShowtimeDto> showtimes;
	private List<MovieDto> movies;
	private AddressDto address;
	private List<TierDto> tiers;


}
