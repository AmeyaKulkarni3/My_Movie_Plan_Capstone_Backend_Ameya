package com.ameya.mymovieplan.exception;

public class RoleAssignmentException extends Exception{

	private static final long serialVersionUID = 7073643069172132008L;
	
	public RoleAssignmentException() {
		super();
	}
	
	public RoleAssignmentException(String errors) {
		super(errors);
	}

}
