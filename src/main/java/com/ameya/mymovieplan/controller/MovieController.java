package com.ameya.mymovieplan.controller;

import java.io.IOException;
import java.util.List;

import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.exception.movie.MovieAlreadyExistsException;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.service.MovieService;

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
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieService movieService;

	@Secured("ROLE_ADMIN")
	@PostMapping
	public ResponseEntity<MovieDto> addMovie(@RequestBody MovieDto dto)
			throws MovieAlreadyExistsException, IOException, NoSuchGenreException, NoSuchLanguageException {

		return ResponseEntity.ok(movieService.addMovie(dto));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MovieDto> getMovieById(@PathVariable int id) throws NoSuchMovieException{
		return ResponseEntity.ok(movieService.getMovieById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<MovieDto>> getAllMovies(){
		return ResponseEntity.ok(movieService.getAllMovies());
	}
	
	@Secured("ROLE_ADMIN")
	@PutMapping
	public ResponseEntity<MovieDto> updateMovie(@RequestBody MovieDto dto) throws NoSuchMovieException{
		return ResponseEntity.ok(movieService.updateMovie(dto));
	}
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteMovie(@PathVariable int id) throws NoSuchMovieException{
		return ResponseEntity.ok(movieService.deleteMovie(id));
	}

}
