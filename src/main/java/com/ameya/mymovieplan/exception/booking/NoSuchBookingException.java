package com.ameya.mymovieplan.exception.booking;

public class NoSuchBookingException extends Exception{

	private static final long serialVersionUID = -4944015893656596933L;
	
	public NoSuchBookingException() {
		super();
	}
	
	public NoSuchBookingException(String errors) {
		super(errors);
	}

}
