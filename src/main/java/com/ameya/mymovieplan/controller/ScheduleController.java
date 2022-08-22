package com.ameya.mymovieplan.controller;

import java.util.List;

import com.ameya.mymovieplan.dto.ScheduleDto;
import com.ameya.mymovieplan.exception.movie.MovieNotActiveException;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.schedule.CantScheduleShowException;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.exception.schedule.ScheduleAlreadyExistsException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.theater.NoSuchTheaterException;
import com.ameya.mymovieplan.model.request.CreateScheduleRequestModel;
import com.ameya.mymovieplan.model.request.UpdateScheduleRequestModel;
import com.ameya.mymovieplan.service.ScheduleService;
import com.ameya.mymovieplan.utils.OutputMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;

	@PostMapping
	public ResponseEntity<ScheduleDto> createSchedule(@RequestBody CreateScheduleRequestModel createSchedule)
			throws ScheduleAlreadyExistsException, NoSuchMovieException, NoSuchTheaterException,
			NoSuchShowtimeException, CantScheduleShowException, MovieNotActiveException {
		return ResponseEntity.ok(scheduleService.createSchedule(createSchedule.getMovieId(),
				createSchedule.getTheaterId(), createSchedule.getShowtimeId(), createSchedule.getDate()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ScheduleDto> getScheduleById(@PathVariable int id) throws NoSuchScheduleException {
		return ResponseEntity.ok(scheduleService.getScheduleById(id));
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<List<ScheduleDto>> getScheduleByMovie(@PathVariable int movieId) {
		return ResponseEntity.ok(scheduleService.getScheduleByMovie(movieId));
	}

	@GetMapping("/theater/{theaterId}")
	public ResponseEntity<List<ScheduleDto>> getScheduleByTheater(@PathVariable int theaterId) {
		return ResponseEntity.ok(scheduleService.getScheduleByTheater(theaterId));
	}

	@GetMapping("/showtime/{showtimeId}")
	public ResponseEntity<List<ScheduleDto>> getScheduleByShowtime(@PathVariable int showtimeId) {
		return ResponseEntity.ok(scheduleService.getScheduleByShowtime(showtimeId));
	}

	@PutMapping
	public ResponseEntity<ScheduleDto> updateSchedule(@RequestBody UpdateScheduleRequestModel updateRequest)
			throws NoSuchScheduleException, NoSuchMovieException, NoSuchShowtimeException, NoSuchTheaterException {
		return ResponseEntity.ok(scheduleService.updateSchedule(updateRequest));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<OutputMessage> deleteSchedule(@PathVariable int id) throws NoSuchScheduleException{
		return ResponseEntity.ok(scheduleService.deleteSchedule(id));
	}

}
