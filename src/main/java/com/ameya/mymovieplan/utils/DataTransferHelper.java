package com.ameya.mymovieplan.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.ameya.mymovieplan.dto.BookingDto;
import com.ameya.mymovieplan.dto.CityDto;
import com.ameya.mymovieplan.dto.MovieDto;
import com.ameya.mymovieplan.dto.ScheduleDto;
import com.ameya.mymovieplan.dto.SeatDto;
import com.ameya.mymovieplan.dto.ShowtimeDto;
import com.ameya.mymovieplan.dto.TheaterDto;
import com.ameya.mymovieplan.dto.UserDto;
import com.ameya.mymovieplan.entity.Booking;
import com.ameya.mymovieplan.entity.City;
import com.ameya.mymovieplan.entity.Movie;
import com.ameya.mymovieplan.entity.RoleEntity;
import com.ameya.mymovieplan.entity.Schedule;
import com.ameya.mymovieplan.entity.Seat;
import com.ameya.mymovieplan.entity.Showtime;
import com.ameya.mymovieplan.entity.Theater;
import com.ameya.mymovieplan.entity.UserEntity;
import com.ameya.mymovieplan.model.request.UserSignupRequestModel;
import com.ameya.mymovieplan.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataTransferHelper {
	
	
	@Autowired
	RoleRepository roleRepository;
	
	public UserDto userRequestToDto(UserSignupRequestModel user) {
		UserDto dto = new UserDto();
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setPassword(user.getPassword());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		return dto;
	}
	
	public UserEntity userDtoToEntity(UserDto dto) {
		UserEntity user = new UserEntity();
		user.setId(dto.getId());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setEmail(dto.getEmail());
		user.setUserId(dto.getUserId());
		user.setPhone(dto.getPhone());
		user.setEncryptedPassword(dto.getPassword());
		
		Collection<RoleEntity> roles = new HashSet<>();
		
		for(String r : dto.getRoles()) {
			RoleEntity role = roleRepository.findByName(r);
			if(role != null) {
				roles.add(role);
			}
		}
		
		user.setRoles(roles);
		
		return user;
	}

	public UserDto userEntityToDto(UserEntity savedUser) {
		UserDto user = new UserDto();
		user.setId(savedUser.getId());
		user.setFirstName(savedUser.getFirstName());
		user.setLastName(savedUser.getLastName());
		user.setEmail(savedUser.getEmail());
		user.setUserId(savedUser.getUserId());
		user.setPhone(savedUser.getPhone());
		Collection<RoleEntity> rolesEntity = savedUser.getRoles();
		Collection<String> roles = new ArrayList<>();
		for(RoleEntity r : rolesEntity) {
			roles.add(r.getName());
		}
		user.setRoles(roles);
		if(savedUser.getBookings() != null) {
			List<Booking> bookings = savedUser.getBookings();
			List<BookingDto> bdtos = new ArrayList<>();
			for(Booking b : bookings ) {
				BookingDto bdto = new BookingDto();
				bdto.setId(b.getId());
				bdto.setTotalPrice(b.getTotalPrice());
				Schedule s = b.getSchedule();
				ScheduleDto sdto = new ScheduleDto();
				
				Movie m = s.getMovie();
				Theater t = s.getTheater();
				Showtime st = s.getShowtime();
				
				MovieDto mdto = new MovieDto();
				mdto.setId(m.getId());
				mdto.setName(m.getName());
				mdto.setPoster(m.getPoster());
				mdto.setActive(m.isActive());
				sdto.setMovie(mdto);
				
				TheaterDto tdto = new TheaterDto();
				tdto.setId(t.getId());
				tdto.setName(t.getName());
				City c = t.getCity();
				CityDto cdto = new CityDto();
				cdto.setId(c.getId());
				cdto.setName(c.getName());
				tdto.setCity(cdto);
				sdto.setTheater(tdto);
				
				ShowtimeDto stdto = new ShowtimeDto();
				stdto.setId(st.getId());
				stdto.setTime(st.getTime());
				sdto.setShowtime(stdto);
				
				sdto.setDate(s.getDate());
				
				bdto.setSchedule(sdto);
				
				List<Seat> seats = b.getSeats();
				List<SeatDto> seatDtos = new ArrayList<>();
				for(Seat seat : seats) {
					SeatDto seatDto = new SeatDto();
					seatDto.setId(seat.getId());
					seatDto.setSeatNumber(seat.getSeatNumber());
					seatDto.setBooked(seat.isBooked());
					seatDtos.add(seatDto);
				}
				bdto.setSeats(seatDtos);
				bdtos.add(bdto);
			}
			user.setBookings(bdtos);
		}
		return user;
	}

}
