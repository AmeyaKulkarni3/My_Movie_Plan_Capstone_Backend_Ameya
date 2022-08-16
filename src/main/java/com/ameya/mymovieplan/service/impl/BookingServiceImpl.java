package com.ameya.mymovieplan.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ameya.mymovieplan.dto.AddressDto;
import com.ameya.mymovieplan.dto.BookingDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.GenreDto;
import com.ameya.mymovieplan.dto.LanguageDto;
import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.dto.ScheduleDto;
import com.ameya.mymovieplan.dto.SeatDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.dto.TierDto;
import com.ameya.mymovieplan.dto.UserDto;
import com.ameya.mymovieplan.entity.Address;
import com.ameya.mymovieplan.entity.Booking;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Genre;
import com.ameya.mymovieplan.entity.Language;
import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.entity.Schedule;
import com.ameya.mymovieplan.entity.Seat;
import com.ameya.mymovieplan.entity.Showtime;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.entity.Tier;
import com.ameya.mymovieplan.entity.UserEntity;
import com.ameya.mymovieplan.exception.ExceptionConstants;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.exception.seat.NoSuchSeatException;
import com.ameya.mymovieplan.model.request.CreateBookingRequestModel;
import com.ameya.mymovieplan.repository.BookingRepository;
import com.ameya.mymovieplan.repository.ScheduleRepository;
import com.ameya.mymovieplan.repository.SeatRepository;
import com.ameya.mymovieplan.repository.TierRepository;
import com.ameya.mymovieplan.repository.UserRepository;
import com.ameya.mymovieplan.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:BookingValidationMessages.properties")
@Transactional
public class BookingServiceImpl implements BookingService {

	@Autowired
	BookingRepository bookingRepository;

	@Autowired
	ScheduleRepository scheduleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	TierRepository tierRepository;

	@Autowired
	Environment env;

	@Override
	public BookingDto createBooking(CreateBookingRequestModel dto) throws NoSuchScheduleException, NoSuchSeatException {

		Schedule schedule = scheduleRepository.findById(dto.getScheduleId()).orElseThrow(
				() -> new NoSuchScheduleException(env.getProperty(ExceptionConstants.SCHEDULE_NOT_FOUND.toString())));
		Booking booking = new Booking();
		List<Integer> seats = dto.getSeatNumbers();
		List<Seat> seatEntities = new ArrayList<>();
		double totalPrice = 0;
		for (Integer num : seats) {
			Seat s = seatRepository.findById(num).orElseThrow(
					() -> new NoSuchSeatException(env.getProperty(ExceptionConstants.SEAT_NOT_FOUND.toString())));
			Tier tier = s.getTier();
			totalPrice = totalPrice + tier.getPrice();
			tier.setSeatsBooked(tier.getSeatsBooked()+1);
			tierRepository.save(tier);
			s.setBooked(true);
			seatEntities.add(s);
		}
		
		UserEntity user = userRepository.findByUserId(dto.getUserId());
		
		booking.setSchedule(schedule);
		booking.setSeats(seatEntities);
		booking.setTotalPrice(totalPrice);
		booking.setUser(user);
		
		Booking saved = bookingRepository.save(booking);
		
		BookingDto bookingDto = dataTransfer(saved);
		
		return bookingDto;
	}
	
	private BookingDto dataTransfer(Booking b) {
		BookingDto dto = new BookingDto();
		dto.setId(b.getId());
		ScheduleDto sdto = new ScheduleDto();
		
		Schedule s = b.getSchedule();
		Movie m = s.getMovie();
		Theater t = s.getTheater();
		Showtime st = s.getShowtime();
		
		MovieDto mdto = new MovieDto();
		mdto.setId(m.getId());
		mdto.setName(m.getName());
		mdto.setDirectors(m.getDirectors());
		mdto.setCast(m.getCast());
		mdto.setDuration(m.getDuration());
		mdto.setReleaseDate(m.getReleaseDate());
		List<Genre> gs = m.getGenres();
		List<GenreDto> gdtos = new ArrayList<>();
		for(Genre g : gs) {
			GenreDto gdto = new GenreDto();
			gdto.setId(g.getId());
			gdto.setName(g.getName());
			gdtos.add(gdto);
		}
		mdto.setGenres(gdtos);
		List<Language> ls = m.getLanguages();
		List<LanguageDto> ldtos = new ArrayList<>();
		for(Language l : ls) {
			LanguageDto ldto = new LanguageDto();
			ldto.setId(l.getId());
			ldto.setName(l.getName());
			ldtos.add(ldto);
		}
		mdto.setLanguages(ldtos);
		
		TheaterDto tdto = new TheaterDto();
		tdto.setId(t.getId());
		tdto.setName(t.getName());
		City c = t.getCity();
		CityDto cdto = new CityDto();
		cdto.setId(c.getId());
		cdto.setName(c.getName());
		tdto.setCity(cdto);
		Address a = t.getAddress();
		AddressDto adto = new AddressDto();
		adto.setId(a.getId());
		adto.setLine1(a.getLine1());
		adto.setLine2(a.getLine2());
		adto.setPincode(a.getPincode());
		adto.setCityDto(cdto);
		tdto.setAddress(adto);
		
		ShowtimeDto stdto = new ShowtimeDto();
		stdto.setId(st.getId());
		stdto.setTime(st.getTime());
		
		sdto.setId(s.getId());
		sdto.setDate(s.getDate());
		sdto.setMovie(mdto);
		sdto.setTheater(tdto);
		sdto.setShowtime(stdto);
		dto.setSchedule(sdto);
		
		List<Seat> seats = b.getSeats();
		List<SeatDto> seatDtos = new ArrayList<>(); 
		for(Seat seat : seats) {
			SeatDto seatDto = new SeatDto();
			seatDto.setId(seat.getId());
			seatDto.setSeatNumber(seat.getSeatNumber());
			seatDto.setBooked(seat.isBooked());
			Tier tier = seat.getTier();
			TierDto tierDto = new TierDto();
			tierDto.setId(tier.getId());
			tierDto.setName(tier.getName());
			tierDto.setNoOfSeats(tier.getNoOfSeats());
			tierDto.setSeatsBooked(tier.getSeatsBooked());
			tierDto.setPrice(tier.getPrice());
			tierDto.setPriority(tier.getPriority());
			seatDto.setTier(tierDto);
			seatDtos.add(seatDto);
		}
		
		dto.setSeats(seatDtos);
		dto.setTotalPrice(b.getTotalPrice());
		
		UserEntity user = b.getUser();
		UserDto udto = new UserDto();
		udto.setId(user.getId());
		udto.setFirstName(user.getFirstName());
		udto.setLastName(user.getLastName());
		udto.setEmail(user.getEmail());
		udto.setPhone(user.getPhone());
		udto.setUserId(user.getUserId());
		dto.setUser(udto);
		
		return dto;
		
	}

	@Override
	public BookingDto getBookingById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingDto getAllUserBookings(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBooking(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
