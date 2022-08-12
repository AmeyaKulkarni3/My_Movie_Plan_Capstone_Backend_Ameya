package com.ameya.mymovieplan.dto;

import lombok.Data;

@Data
public class AddressDto {
	
	private int id;
	private String line1;
	private String line2;
	private CityDto cityDto;
	private String pincode;

}
