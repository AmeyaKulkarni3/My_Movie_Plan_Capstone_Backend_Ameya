package com.ameya.mymovieplan.controller;

import java.util.List;

import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;
import com.ameya.mymovieplan.exception.theater.NoSuchTheaterException;
import com.ameya.mymovieplan.exception.theater.TheaterAlreadyExistsException;
import com.ameya.mymovieplan.exception.tier.NoSuchTierException;
import com.ameya.mymovieplan.exception.tier.TierAlreadyExistsException;
import com.ameya.mymovieplan.model.request.CreateScheduleRequestModel;
import com.ameya.mymovieplan.service.TheaterService;

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
@RequestMapping("/theater")
public class TheaterController {

	@Autowired
	TheaterService theaterService;

	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<TheaterDto> addTheater(@RequestBody TheaterDto dto)
			throws TheaterAlreadyExistsException, TierAlreadyExistsException, ShowtimeAlreadyExistsException,
			NoSuchMovieException, NoSuchTierException, NoSuchTheaterException {

		return ResponseEntity.ok(theaterService.addTheater(dto));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TheaterDto> getTheater(@PathVariable int id) throws NoSuchTheaterException {

		return ResponseEntity.ok(theaterService.getTheaterById(id));
	}

	@GetMapping
	public ResponseEntity<List<TheaterDto>> getAllTheaters() {

		return ResponseEntity.ok(theaterService.getAllTheaters());
	}

	@Secured("ROLE_ADMIN")
	@PutMapping
	public ResponseEntity<TheaterDto> updateTheater(@RequestBody TheaterDto dto) throws NoSuchTheaterException {
		return ResponseEntity.ok(theaterService.updateTheater(dto));
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTheater(@PathVariable int id) throws NoSuchTheaterException {
		return ResponseEntity.ok(theaterService.deleteTheater(id));
	}

}
