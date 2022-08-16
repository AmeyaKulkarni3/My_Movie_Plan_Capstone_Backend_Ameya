package com.ameya.mymovieplan.exception.booking;

public class BookingAlreadyExistsException extends Exception{

	private static final long serialVersionUID = -4944015893656596933L;
	
	public BookingAlreadyExistsException() {
		super();
	}
	
	public BookingAlreadyExistsException(String errors) {
		super(errors);
	}

}
