package com.example.demo.exception;

public class PrescriptionNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5734978651332597675L;

	public PrescriptionNotFound(String msg) {
		super(msg);
	}
}
