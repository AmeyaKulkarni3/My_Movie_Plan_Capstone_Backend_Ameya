package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.exception.city.CityAlreadyExistsException;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;
import com.ameya.mymovieplan.exception.theater.NoSuchTheaterException;
import com.ameya.mymovieplan.exception.theater.TheaterAlreadyExistsException;
import com.ameya.mymovieplan.exception.tier.NoSuchTierException;
import com.ameya.mymovieplan.exception.tier.TierAlreadyExistsException;
import com.ameya.mymovieplan.model.request.CreateScheduleRequestModel;
import com.ameya.mymovieplan.utils.OutputMessage;

public interface TheaterService {

	TheaterDto addTheater(TheaterDto theater)
			throws TheaterAlreadyExistsException, TierAlreadyExistsException, ShowtimeAlreadyExistsException,
			NoSuchMovieException, NoSuchTierException, NoSuchTheaterException, NoSuchCityException;

	TheaterDto getTheaterById(int id) throws NoSuchTheaterException;

	List<TheaterDto> getAllTheaters();

	TheaterDto updateTheater(TheaterDto theaterDto) throws NoSuchTheaterException;

	OutputMessage deleteTheater(int id) throws NoSuchTheaterException;

}
