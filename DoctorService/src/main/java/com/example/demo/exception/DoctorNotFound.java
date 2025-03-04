package com.example.demo.exception;

@SuppressWarnings("serial")
public class DoctorNotFound extends RuntimeException{
	
	public DoctorNotFound(String message) {
		super(message);
	}

}
