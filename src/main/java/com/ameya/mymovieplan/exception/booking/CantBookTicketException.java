package com.ameya.mymovieplan.exception.booking;

public class CantBookTicketException extends Exception{

	private static final long serialVersionUID = -4944015893656596933L;
	
	public CantBookTicketException() {
		super();
	}
	
	public CantBookTicketException(String errors) {
		super(errors);
	}

}
