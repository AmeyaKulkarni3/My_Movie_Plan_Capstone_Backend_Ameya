package com.ameya.mymovieplan.controller;

import java.util.List;

import com.ameya.mymovieplan.dto.BookingDto;
import com.ameya.mymovieplan.exception.schedule.NoSuchScheduleException;
import com.ameya.mymovieplan.exception.seat.NoSuchSeatException;
import com.ameya.mymovieplan.model.request.CreateBookingRequestModel;
import com.ameya.mymovieplan.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping
	public ResponseEntity<BookingDto> createBooking(@RequestBody CreateBookingRequestModel createBooking)
			throws NoSuchScheduleException, NoSuchSeatException {

		return ResponseEntity.ok(bookingService.createBooking(createBooking));

	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<List<BookingDto>> getAllBookings(@PathVariable String userId){
		return ResponseEntity.ok(bookingService.getAllUserBookings(userId));
	}

}
