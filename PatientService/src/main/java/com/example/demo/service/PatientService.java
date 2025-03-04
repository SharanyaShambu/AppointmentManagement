package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Patient;
import com.example.demo.exception.PatientNotFound;

public interface PatientService {

	public ResponseEntity<Patient> addPatient(int id,Patient patient);

	public ResponseEntity<Patient> updatePatientDetails(int patientId, Patient patient) throws PatientNotFound;

	public ResponseEntity<String> deletePatient(int id);

	public ResponseEntity<Patient> findByPatientId(int id);
	
	public ResponseEntity<List<Patient>> findAll();

}
