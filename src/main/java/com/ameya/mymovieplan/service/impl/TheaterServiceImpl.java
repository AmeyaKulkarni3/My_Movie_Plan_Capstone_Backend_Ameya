package com.ameya.mymovieplan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ameya.mymovieplan.dto.AddressDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.dto.ScheduleDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.dto.TierDto;
import com.ameya.mymovieplan.entity.Address;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Genre;
import com.ameya.mymovieplan.entity.Language;
import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.entity.Schedule;
import com.ameya.mymovieplan.entity.Showtime;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.entity.Tier;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.city.NoSuchCityException;
import com.ameya.mymovieplan.exception.movie.NoSuchMovieException;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;
import com.ameya.mymovieplan.exception.theater.NoSuchTheaterException;
import com.ameya.mymovieplan.exception.theater.TheaterAlreadyExistsException;
import com.ameya.mymovieplan.exception.tier.NoSuchTierException;
import com.ameya.mymovieplan.exception.tier.TierAlreadyExistsException;
import com.ameya.mymovieplan.model.request.CreateScheduleRequestModel;
import com.ameya.mymovieplan.repository.AddressRepository;
import com.ameya.mymovieplan.repository.CityRepository;
import com.ameya.mymovieplan.repository.MovieRepository;
import com.ameya.mymovieplan.repository.ShowtimeRepository;
import com.ameya.mymovieplan.repository.TheaterRepository;
import com.ameya.mymovieplan.repository.TierRepository;
import com.ameya.mymovieplan.service.MovieService;
import com.ameya.mymovieplan.service.ShowtimeService;
import com.ameya.mymovieplan.service.TheaterService;
import com.ameya.mymovieplan.service.TierService;
import com.ameya.mymovieplan.utils.CrudMessage;
import com.ameya.mymovieplan.utils.OutputMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("TheaterValidationMessages.properties")
@Transactional
public class TheaterServiceImpl implements TheaterService {

	@Autowired
	TheaterRepository theaterRepository;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	TierService tierService;

	@Autowired
	ShowtimeRepository showtimeRepository;

	@Autowired
	ShowtimeService showtimeService;

	@Autowired
	TierRepository tierRepository;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	MovieService movieService;

	@Autowired
	Environment env;

	@Override
	public TheaterDto addTheater(TheaterDto theater) throws TheaterAlreadyExistsException, TierAlreadyExistsException,
			ShowtimeAlreadyExistsException, NoSuchMovieException, NoSuchTierException, NoSuchTheaterException, NoSuchCityException {

		Theater saved = theaterRepository.findByName(theater.getName());

		if (saved != null) {
			throw new TheaterAlreadyExistsException(
					env.getProperty(ExceptionConstants.THEATER_ALREADY_EXISTS.toString()));
		}

		Theater t = new Theater();
		t.setName(theater.getName());

		City c = cityRepository.findById(theater.getCity().getId()).orElseThrow(() -> new NoSuchCityException(env.getProperty(ExceptionConstants.CITY_NOT_FOUND.toString())));

		t.setCity(c);

		Address a = new Address();
		a.setLine1(theater.getAddress().getLine1());
		a.setLine2(theater.getAddress().getLine2());
		a.setPincode(theater.getAddress().getPincode());
		a.setCity(c);

		Address savedAddress = addressRepository.save(a);

		t.setAddress(savedAddress);

		List<TierDto> tiers = theater.getTiers();
		Collections.sort(tiers);
		List<Tier> tierEntities = new ArrayList<>();
		for (TierDto tdto : tiers) {
			if (tdto.getId() != 0) {
				Tier savedTier = tierRepository.findById(tdto.getId()).orElseThrow(
						() -> new NoSuchTierException(env.getProperty(ExceptionConstants.TIER_NOT_FOUND.toString())));
				tierEntities.add(savedTier);
			} else {
//				TierDto savedTierDto = tierService.addTier(tdto);
//				Tier tEntity = new Tier();
//				tEntity.setId(savedTierDto.getId());
//				tEntity.setName(savedTierDto.getName());
//				tEntity.setPrice(savedTierDto.getPrice());
//				tEntity.setPriority(savedTierDto.getPriority());
//				tEntity.setSeats(savedTierDto.getSeats());
//				tierEntities.add(tEntity);
				Tier tEntity = new Tier();
				tEntity.setName(tdto.getName());
				tEntity.setPrice(tdto.getPrice());
				tEntity.setPriority(tdto.getPriority());
				tEntity.setNoOfSeats(tdto.getNoOfSeats());
				tEntity.setRows(tdto.getRows());
				tEntity.setCols(tdto.getCols());
				tEntity.setSeatsBooked(0);

				tierEntities.add(tEntity);
			}

		}

		t.setTiers(tierEntities);
		t.setSchedules(new ArrayList<Schedule>());

		Theater savedTheater = theaterRepository.save(t);
		
		List<Tier> savedTiers = savedTheater.getTiers();
		for(Tier ti : savedTiers) {
			List<Theater> theaters = ti.getTheaters();
			theaters.add(savedTheater);
			ti.setTheaters(theaters);
			tierRepository.save(ti);
		}
		
		Address add = savedTheater.getAddress();
		add.setTheater(savedTheater);
		addressRepository.save(add);

		TheaterDto returnValue = dataTransfer(theaterRepository.findById(savedTheater.getId()).orElseThrow(() -> new NoSuchTheaterException(env.getProperty(ExceptionConstants.THEATER_NOT_FOUND.toString()))));

		return returnValue;
	}

	private TheaterDto dataTransfer(Theater t) {
		TheaterDto dto = new TheaterDto();
		dto.setId(t.getId());
		dto.setName(t.getName());
		Address a = t.getAddress();
		AddressDto adto = new AddressDto();
		adto.setId(a.getId());
		adto.setLine1(a.getLine1());
		adto.setLine2(a.getLine2());
		adto.setPincode(a.getPincode());
		CityDto cdto = new CityDto();
		City c = t.getCity();
		cdto.setId(c.getId());
		cdto.setName(c.getName());
		adto.setCityDto(cdto);
		dto.setAddress(adto);
		dto.setCity(cdto);
		List<Tier> tiers = t.getTiers();
		List<TierDto> tdtos = new ArrayList<>();
		for (Tier tier : tiers) {
			TierDto tdto = new TierDto();
			tdto.setId(tier.getId());
			tdto.setName(tier.getName());
			tdto.setPrice(tier.getPrice());
			tdto.setPriority(tier.getPriority());
			tdto.setNoOfSeats(tier.getNoOfSeats());
			tdto.setRows(tier.getRows());
			tdto.setCols(tier.getCols());
			tdtos.add(tdto);
		}
		dto.setTiers(tdtos);
		List<Schedule> schedules = t.getSchedules();
		List<ScheduleDto> sdtos = new ArrayList<>();
		for (Schedule s : schedules) {
			ScheduleDto sdto = new ScheduleDto();
			sdto.setId(s.getId());
			Movie m = s.getMovie();
			Showtime st = s.getShowtime();
			MovieDto mdto = new MovieDto();
			mdto.setId(m.getId());
			mdto.setName(m.getName());
			mdto.setDirectors(m.getDirectors());
			mdto.setCast(m.getCast());
			mdto.setActive(m.isActive());
			mdto.setDuration(m.getDuration());
			mdto.setPoster(m.getPoster());
			mdto.setReleaseDate(m.getReleaseDate());
			List<Genre> gs = m.getGenres();
			List<GenreDto> gdtos = new ArrayList<>();
			for (Genre g : gs) {
				GenreDto gdto = new GenreDto();
				gdto.setId(g.getId());
				gdto.setName(g.getName());
				gdtos.add(gdto);
			}
			mdto.setGenres(gdtos);
			List<Language> ls = m.getLanguages();
			List<LanguageDto> ldtos = new ArrayList<>();
			for (Language l : ls) {
				LanguageDto ldto = new LanguageDto();
				ldto.setId(l.getId());
				ldto.setName(l.getName());
				ldtos.add(ldto);
			}
			mdto.setLanguages(ldtos);
			sdto.setMovie(mdto);

			ShowtimeDto stdto = new ShowtimeDto();
			stdto.setId(st.getId());
			stdto.setTime(st.getTime());

			sdto.setShowtime(stdto);

			sdtos.add(sdto);
		}
		dto.setSchedules(sdtos);
		return dto;
	}

	@Override
	public TheaterDto getTheaterById(int id) throws NoSuchTheaterException {
		Theater saved = theaterRepository.findById(id).orElseThrow(
				() -> new NoSuchTheaterException(env.getProperty(ExceptionConstants.THEATER_NOT_FOUND.toString())));
		TheaterDto dto = dataTransfer(saved);
		return dto;
	}

	@Override
	public List<TheaterDto> getAllTheaters() {
		List<Theater> theaters = (List<Theater>) theaterRepository.findAll();
		List<TheaterDto> tdtos = new ArrayList<>();
		for (Theater t : theaters) {
			TheaterDto tdto = dataTransfer(t);
			tdtos.add(tdto);
		}
		return tdtos;
	}

	@Override
	public TheaterDto updateTheater(TheaterDto theaterDto) throws NoSuchTheaterException {

		Theater theater = theaterRepository.findById(theaterDto.getId()).orElseThrow(
				() -> new NoSuchTheaterException(env.getProperty(ExceptionConstants.THEATER_NOT_FOUND.toString())));

		if (theaterDto.getName() != null && !theater.getName().equals(theaterDto.getName())) {
			theater.setName(theaterDto.getName());
		}

		Theater saved = theaterRepository.save(theater);

		return dataTransfer(saved);
	}

	@Override
	public OutputMessage deleteTheater(int id) throws NoSuchTheaterException {
		Theater theater = theaterRepository.findById(id).orElseThrow(
				() -> new NoSuchTheaterException(env.getProperty(ExceptionConstants.THEATER_NOT_FOUND.toString())));
		theaterRepository.delete(theater);
		OutputMessage message = new OutputMessage();
		message.setMessage(env.getProperty(CrudMessage.THEATER_DELETE_SUCCESS.toString()));
		return message;
	}

}
