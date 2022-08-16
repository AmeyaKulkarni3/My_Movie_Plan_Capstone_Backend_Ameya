package com.ameya.mymovieplan.service;

import java.time.LocalDate;
import java.util.List;

import com.ameya.mymovieplan.dto.ScheduleDto;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.schedule.CantScheduleShowException;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.exception.schedule.ScheduleAlreadyExistsException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.theater.NoSuchTheaterException;
import com.ameya.mymovieplan.model.request.UpdateScheduleRequestModel;

public interface ScheduleService {

	ScheduleDto createSchedule(int movieId, int theaterId, int showtimeId, LocalDate date) throws ScheduleAlreadyExistsException,
			NoSuchMovieException, NoSuchTheaterException, NoSuchShowtimeException, CantScheduleShowException;

	ScheduleDto getScheduleById(int id) throws NoSuchScheduleException;

	List<ScheduleDto> getScheduleByMovie(int movieId);

	List<ScheduleDto> getScheduleByTheater(int theaterId);

	List<ScheduleDto> getScheduleByShowtime(int showtimeId);

	ScheduleDto updateSchedule(UpdateScheduleRequestModel dto)
			throws NoSuchScheduleException, NoSuchMovieException, NoSuchShowtimeException, NoSuchTheaterException;
	
	String deleteSchedule(int id) throws NoSuchScheduleException;
	

}
