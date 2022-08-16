package com.ameya.mymovieplan.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.ameya.mymovieplan.dto.AddressDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.dto.ScheduleDto;
import com.ameya.mymovieplan.dto.SeatDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.dto.TierDto;
import com.ameya.mymovieplan.entity.Address;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Genre;
import com.ameya.mymovieplan.entity.Language;
import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.entity.Schedule;
import com.ameya.mymovieplan.entity.Seat;
import com.ameya.mymovieplan.entity.Showtime;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.entity.Tier;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.schedule.CantScheduleShowException;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.exception.schedule.ScheduleAlreadyExistsException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.theater.NoSuchTheaterException;
import com.ameya.mymovieplan.model.request.UpdateScheduleRequestModel;
import com.ameya.mymovieplan.repository.MovieRepository;
import com.ameya.mymovieplan.repository.ScheduleRepository;
import com.ameya.mymovieplan.repository.SeatRepository;
import com.ameya.mymovieplan.repository.ShowtimeRepository;
import com.ameya.mymovieplan.repository.TheaterRepository;
import com.ameya.mymovieplan.service.ScheduleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:ScheduleValidationMessages.properties")
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

	@Autowired
	ScheduleRepository scheduleRepository;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	TheaterRepository theaterRepository;

	@Autowired
	ShowtimeRepository showtimeRepository;

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	Environment env;

	@Override
	public ScheduleDto createSchedule(int movieId, int theaterId, int showtimeId, LocalDate date) throws ScheduleAlreadyExistsException,
			NoSuchMovieException, NoSuchTheaterException, NoSuchShowtimeException, CantScheduleShowException {

		Schedule savedSchedule = scheduleRepository.findExistingSchedule(movieId, theaterId, showtimeId);
		if (savedSchedule != null) {
			throw new ScheduleAlreadyExistsException(
					env.getProperty(ExceptionConstants.SCHEDULE_ALREADY_EXISTS.toString()));
		}
		Movie movie = movieRepository.findById(movieId).orElseThrow(
				() -> new NoSuchMovieException(env.getProperty(ExceptionConstants.MOVIE_NOT_FOUND.toString())));
		Theater theater = theaterRepository.findById(theaterId).orElseThrow(
				() -> new NoSuchTheaterException(env.getProperty(ExceptionConstants.THEATER_NOT_FOUND.toString())));
		Showtime showtime = showtimeRepository.findById(showtimeId).orElseThrow(
				() -> new NoSuchShowtimeException(env.getProperty(ExceptionConstants.SHOWTIME_NOT_FOUND.toString())));

		List<Schedule> movieSchedules = theater.getSchedules();

		if (movieSchedules.size() != 0) {
			Collections.sort(movieSchedules, new Comparator<Schedule>() {

				@Override
				public int compare(Schedule o1, Schedule o2) {

					int v1 = o1.getDate().compareTo(o2.getDate());
					if (v1 == 0) {
						int v2 = o1.getShowtime().getTime().compareTo(o2.getShowtime().getTime());
						if (v2 != 0) {
							return v2;
						}
					} else {
						return v1;
					}
					return 0;
				}
			});

			Schedule last = movieSchedules.get(movieSchedules.size() - 1);

			int duration = Integer.parseInt(movie.getDuration());

			LocalTime newTime = last.getShowtime().getTime().plusMinutes(duration).plusMinutes(15);

			if (showtime.getTime().isBefore(newTime)) {
				throw new CantScheduleShowException(
						env.getProperty(ExceptionConstants.SCHEDULE_NOT_POSSIBLE.toString()) + " " + newTime.toString());
			}
		}

		Schedule schedule = new Schedule();
		schedule.setMovie(movie);
		schedule.setTheater(theater);
		schedule.setShowtime(showtime);
		schedule.setDate(date);

		Schedule saved = scheduleRepository.save(schedule);

		List<Seat> seats = new ArrayList<>();
		List<Tier> tiers = theater.getTiers();
		Collections.sort(tiers);
		char startingChar = 'A';
		for (Tier t : tiers) {
			for (int i = 1; i <= t.getRows(); i++) {
				for (int j = 1; j <= t.getCols(); j++) {
					Seat seat = new Seat();
					seat.setSeatNumber(String.valueOf(startingChar) + String.valueOf(j));
					seat.setBooked(false);
					seat.setTier(t);
					seat.setSchedule(saved);
					seatRepository.save(seat);
					seats.add(seat);
				}
				startingChar = (char) (startingChar + 1);
			}
		}

		saved.setSeats(seats);

		scheduleRepository.save(saved);

		ScheduleDto dto = dataTransfer(saved);

		return dto;
	}

	private ScheduleDto dataTransfer(Schedule s) {
		ScheduleDto dto = new ScheduleDto();
		dto.setId(s.getId());

		Movie m = s.getMovie();
		Theater t = s.getTheater();
		Showtime st = s.getShowtime();

		MovieDto mdto = new MovieDto();
		mdto.setId(m.getId());
		mdto.setName(m.getName());
		mdto.setDirectors(m.getDirectors());
		mdto.setCast(m.getCast());
		mdto.setDuration(m.getDuration());
		mdto.setPoster(m.getPoster());
		mdto.setActive(m.isActive());
		mdto.setReleaseDate(m.getReleaseDate());
		List<Genre> mgs = m.getGenres();
		List<GenreDto> gdtos = new ArrayList<>();
		for (Genre g : mgs) {
			GenreDto gdto = new GenreDto();
			gdto.setId(g.getId());
			gdto.setName(g.getName());
			gdtos.add(gdto);
		}
		mdto.setGenres(gdtos);
		List<Language> mls = m.getLanguages();
		List<LanguageDto> ldtos = new ArrayList<>();
		for (Language l : mls) {
			LanguageDto ldto = new LanguageDto();
			ldto.setId(l.getId());
			ldto.setName(l.getName());
			ldtos.add(ldto);
		}
		mdto.setLanguages(ldtos);

		dto.setMovie(mdto);

		TheaterDto tdto = new TheaterDto();
		tdto.setId(t.getId());
		tdto.setName(t.getName());
		Address a = t.getAddress();
		City c = t.getCity();
		CityDto cdto = new CityDto();
		cdto.setId(c.getId());
		cdto.setName(c.getName());
		AddressDto adto = new AddressDto();
		adto.setId(a.getId());
		adto.setCityDto(cdto);
		adto.setLine1(a.getLine1());
		adto.setLine2(a.getLine2());
		adto.setPincode(a.getPincode());
		tdto.setCity(cdto);
		tdto.setAddress(adto);
		List<Tier> tiers = t.getTiers();
		List<TierDto> trDtos = new ArrayList<>();
		for (Tier tr : tiers) {
			TierDto trDto = new TierDto();
			trDto.setId(tr.getId());
			trDto.setName(tr.getName());
			trDto.setPrice(tr.getPrice());
			trDto.setPriority(tr.getPriority());
			trDto.setNoOfSeats(tr.getNoOfSeats());
			trDto.setRows(tr.getRows());
			trDto.setCols(tr.getCols());
			trDto.setSeatsBooked(tr.getSeatsBooked());
			trDtos.add(trDto);
		}
		tdto.setTiers(trDtos);
		dto.setTheater(tdto);

		ShowtimeDto stdto = new ShowtimeDto();
		stdto.setId(st.getId());
		stdto.setTime(st.getTime());
		dto.setShowtime(stdto);

		List<Seat> seats = s.getSeats();
		List<SeatDto> sdtos = new ArrayList<>();
		for (Seat seat : seats) {
			SeatDto sdto = new SeatDto();
			sdto.setId(seat.getId());
			sdto.setSeatNumber(seat.getSeatNumber());
			sdto.setBooked(seat.isBooked());
			Tier tier = seat.getTier();
			TierDto tierDto = new TierDto();
			tierDto.setId(tier.getId());
			tierDto.setName(tier.getName());
			tierDto.setNoOfSeats(tier.getNoOfSeats());
			tierDto.setPrice(tier.getPrice());
			tierDto.setPriority(tier.getPriority());
			tierDto.setRows(tier.getRows());
			tierDto.setCols(tier.getCols());
			tierDto.setSeatsBooked(tier.getSeatsBooked());
			sdto.setTier(tierDto);
			sdtos.add(sdto);
		}
		dto.setSeats(sdtos);

		return dto;
	}

	@Override
	public ScheduleDto getScheduleById(int id) throws NoSuchScheduleException {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(
				() -> new NoSuchScheduleException(env.getProperty(ExceptionConstants.SCHEDULE_NOT_FOUND.toString())));
		return dataTransfer(schedule);
	}

	@Override
	public List<ScheduleDto> getScheduleByMovie(int movieId) {
		List<Schedule> schedules = scheduleRepository.findByMovie(movieId);
		List<ScheduleDto> sdtos = new ArrayList<>();
		for (Schedule s : schedules) {
			ScheduleDto sdto = dataTransfer(s);
			sdtos.add(sdto);
		}
		return sdtos;
	}

	@Override
	public List<ScheduleDto> getScheduleByTheater(int theaterId) {
		List<Schedule> schedules = scheduleRepository.findByTheater(theaterId);
		List<ScheduleDto> sdtos = new ArrayList<>();
		for (Schedule s : schedules) {
			ScheduleDto sdto = dataTransfer(s);
			sdtos.add(sdto);
		}
		return sdtos;
	}

	@Override
	public List<ScheduleDto> getScheduleByShowtime(int showtimeId) {
		List<Schedule> schedules = scheduleRepository.findByShowtime(showtimeId);
		List<ScheduleDto> sdtos = new ArrayList<>();
		for (Schedule s : schedules) {
			ScheduleDto sdto = dataTransfer(s);
			sdtos.add(sdto);
		}
		return sdtos;
	}

	@Override
	public ScheduleDto updateSchedule(UpdateScheduleRequestModel dto)
			throws NoSuchScheduleException, NoSuchMovieException, NoSuchShowtimeException, NoSuchTheaterException {
		Schedule schedule = scheduleRepository.findById(dto.getId()).orElseThrow(
				() -> new NoSuchScheduleException(env.getProperty(ExceptionConstants.SCHEDULE_NOT_FOUND.toString())));

		if (dto.getMovieId() != schedule.getMovie().getId()) {
			Movie newMovie = movieRepository.findById(dto.getMovieId()).orElseThrow(
					() -> new NoSuchMovieException(env.getProperty(ExceptionConstants.MOVIE_NOT_FOUND.toString())));
			schedule.setMovie(newMovie);
		}
		if (dto.getShowtimeId() != schedule.getShowtime().getId()) {
			Showtime newShowtime = showtimeRepository.findById(dto.getShowtimeId())
					.orElseThrow(() -> new NoSuchShowtimeException(
							env.getProperty(ExceptionConstants.SHOWTIME_NOT_FOUND.toString())));
			schedule.setShowtime(newShowtime);
		}
		if (dto.getTheaterId() != schedule.getTheater().getId()) {
			Theater newTheater = theaterRepository.findById(dto.getTheaterId()).orElseThrow(
					() -> new NoSuchTheaterException(env.getProperty(ExceptionConstants.THEATER_NOT_FOUND.toString())));
			schedule.setTheater(newTheater);
		}
		return dataTransfer(schedule);
	}

	@Override
	public String deleteSchedule(int id) throws NoSuchScheduleException {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(
				() -> new NoSuchScheduleException(env.getProperty(ExceptionConstants.SCHEDULE_NOT_FOUND.toString())));
		List<Seat> seats = seatRepository.findBySchedule(id);
		for(Seat s : seats) {
			seatRepository.delete(s);
		}
		scheduleRepository.delete(schedule);
		return "Schedule Deleted Successfully!";
	}

}
