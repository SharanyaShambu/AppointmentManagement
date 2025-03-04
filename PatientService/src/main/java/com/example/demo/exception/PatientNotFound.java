package com.example.demo.exception;

@SuppressWarnings("serial")
public class PatientNotFound extends RuntimeException {
	public PatientNotFound(String message) {
		super(message);
	}

}
