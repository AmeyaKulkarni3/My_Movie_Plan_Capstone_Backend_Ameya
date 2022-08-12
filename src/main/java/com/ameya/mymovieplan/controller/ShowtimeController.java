package com.ameya.mymovieplan.controller;

import java.util.List;

import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.exception.genre.GenreAlreadyExistsException;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.exception.language.LanguageAlreadyExistsException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;
import com.ameya.mymovieplan.service.GenreService;
import com.ameya.mymovieplan.service.LanguageService;
import com.ameya.mymovieplan.service.ShowtimeService;

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
@RequestMapping("/admin/showtime")
public class ShowtimeController {

	@Autowired
	ShowtimeService showtimeService;

	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<ShowtimeDto> addShowtime(@RequestBody ShowtimeDto showtimeDto)
			throws ShowtimeAlreadyExistsException {

		return ResponseEntity.ok(showtimeService.addShowtime(showtimeDto));

	}

	@GetMapping("/{id}")
	public ResponseEntity<ShowtimeDto> getShowtimeById(@PathVariable int id) throws NoSuchShowtimeException {

		return ResponseEntity.ok(showtimeService.getShowtimeById(id));
	}

	@GetMapping
	public ResponseEntity<List<ShowtimeDto>> getAllShowtimes() {

		return ResponseEntity.ok(showtimeService.getAllShowtimes());
	}

	@Secured("ROLE_ADMIN")
	@PutMapping
	public ResponseEntity<ShowtimeDto> updateShowtime(@RequestBody ShowtimeDto showtimeDto)
			throws NoSuchShowtimeException {
		return ResponseEntity.ok(showtimeService.updateShowtime(showtimeDto));
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteShowtime(@PathVariable int id) throws NoSuchShowtimeException {
		return ResponseEntity.ok(showtimeService.deleteShowtime(id));
	}

}
