package com.ameya.mymovieplan.service;

import java.io.IOException;
import java.util.List;

import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.exception.genre.NoSuchGenreException;
import com.ameya.mymovieplan.exception.language.NoSuchLanguageException;
import com.ameya.mymovieplan.exception.movie.MovieAlreadyExistsException;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.utils.OutputMessage;

import org.springframework.web.multipart.MultipartFile;

public interface MovieService {

	MovieDto addMovie(MovieDto mdto)
			throws MovieAlreadyExistsException, IOException, NoSuchGenreException, NoSuchLanguageException;

	MovieDto getMovieById(int id) throws NoSuchMovieException;

	List<MovieDto> getAllMovies();

	MovieDto updateMovie(MovieDto dto) throws NoSuchMovieException;

	OutputMessage deleteMovie(int id) throws NoSuchMovieException, NoSuchScheduleException;

	MovieDto updateStatus(int id) throws NoSuchMovieException, NoSuchScheduleException;

	List<MovieDto> getAllMoviesAdmin();

}
