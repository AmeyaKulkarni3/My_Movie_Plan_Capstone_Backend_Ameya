package com.ameya.mymovieplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ameya.mymovieplan.dto.AddressDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.entity.Address;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Showtime;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.showtime.NoSuchShowtimeException;
import com.ameya.mymovieplan.exception.showtime.ShowtimeAlreadyExistsException;
import com.ameya.mymovieplan.repository.ShowtimeRepository;
import com.ameya.mymovieplan.service.ShowtimeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("ShowtimeValidationMessages.properties")
@Transactional
public class ShowtimeServiceImpl implements ShowtimeService {

	@Autowired
	ShowtimeRepository showtimeRepository;

	@Autowired
	Environment env;

	@Override
	public ShowtimeDto addShowtime(ShowtimeDto dto) throws ShowtimeAlreadyExistsException {

		Showtime showtime = showtimeRepository.findByTime(dto.getTime());

		if (showtime != null) {
			throw new ShowtimeAlreadyExistsException(
					env.getProperty(ExceptionConstants.SHOWTIME_ALREADY_EXISTS.toString()));
		}

		Showtime st = new Showtime();
		st.setTime(dto.getTime());

		Showtime saved = showtimeRepository.save(st);

		ShowtimeDto sdto = new ShowtimeDto();

		sdto.setId(saved.getId());
		sdto.setTime(saved.getTime());

		return sdto;
	}
	
	private ShowtimeDto dataTransfer(Showtime st) {
		ShowtimeDto sdto = new ShowtimeDto();
		sdto.setId(st.getId());
		sdto.setTime(st.getTime());
		return sdto;
	}

	@Override
	public ShowtimeDto getShowtimeById(int id) throws NoSuchShowtimeException {
		Showtime showtime = showtimeRepository.findById(id).orElseThrow(
				() -> new NoSuchShowtimeException(env.getProperty(ExceptionConstants.SHOWTIME_NOT_FOUND.toString())));
		ShowtimeDto sdto = dataTransfer(showtime);
		return sdto;
	}

	@Override
	public List<ShowtimeDto> getAllShowtimes() {
		List<Showtime> showtimes = (List<Showtime>) showtimeRepository.findAll();
		List<ShowtimeDto> sdtos = new ArrayList<>();
		for(Showtime s : showtimes) {
			ShowtimeDto sdto = dataTransfer(s);
			sdtos.add(sdto);
		}
		return sdtos;
	}

	@Override
	public ShowtimeDto updateShowtime(ShowtimeDto dto) throws NoSuchShowtimeException {
		
		Showtime showtime = showtimeRepository.findById(dto.getId()).orElseThrow(
				() -> new NoSuchShowtimeException(env.getProperty(ExceptionConstants.SHOWTIME_NOT_FOUND.toString())));
		
		showtime.setTime(dto.getTime());
		Showtime saved = showtimeRepository.save(showtime);
		ShowtimeDto sdto = dataTransfer(saved);
		return sdto;
	}

	@Override
	public String deleteShowtime(int id) throws NoSuchShowtimeException {
		Showtime showtime = showtimeRepository.findById(id).orElseThrow(
				() -> new NoSuchShowtimeException(env.getProperty(ExceptionConstants.SHOWTIME_NOT_FOUND.toString())));
		
		showtimeRepository.delete(showtime);
		return "Deleted Showtime Successfully!";
	}

}
