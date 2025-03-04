package com.example.demo.exception;

public class AppointmentNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7539911111644358707L;

	public AppointmentNotFoundException(String message) {
		super(message);
	}

}
