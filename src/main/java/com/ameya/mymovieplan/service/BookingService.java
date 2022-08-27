package com.ameya.mymovieplan.service;

import java.util.List;

import com.ameya.mymovieplan.dto.BookingDto;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.exception.seat.NoSuchSeatException;
import com.ameya.mymovieplan.model.request.CreateBookingRequestModel;

public interface BookingService {
	
	BookingDto createBooking(CreateBookingRequestModel dto) throws NoSuchScheduleException, NoSuchSeatException;
	
	BookingDto getBookingById(int id);
	
	List<BookingDto> getAllUserBookings(String userId);
	
	String deleteBooking(int id);

}
