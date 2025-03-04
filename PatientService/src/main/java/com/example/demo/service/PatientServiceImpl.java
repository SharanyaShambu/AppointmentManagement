package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.client.PatClient;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Patient;
import com.example.demo.exception.PatientNotFound;
import com.example.demo.repository.PatientRepository;

import lombok.AllArgsConstructor;

// Marks this class as a Spring service
@Service
// Generates a constructor with one parameter for each field in this class
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

	// Dependencies for repository and external interfaces
	private final PatientRepository repository;
	
	private final PatClient client;

	// Adds a new patient to the repository
	@Override
	public ResponseEntity<Patient> addPatient(int id,Patient patient) {
		UserDto dto= client.getPatInfo(id);
		if(dto.getRoles().equalsIgnoreCase("patient")) {
		patient.setPatientID(id);
		patient.setEmailID(dto.getEmail());
		patient.setPatientName(dto.getName());
		Patient pat = repository.save(patient);
		return new ResponseEntity<>(pat, HttpStatus.CREATED);
		}
		else throw new PatientNotFound("No Patient with id " + id);
	}

	// Updates patient details if the patient is found in the repository
	@Override
	public ResponseEntity<Patient> updatePatientDetails(int patientId, Patient updatedPatient) throws PatientNotFound {
		Optional<Patient> optional = repository.findById(patientId);
		if (optional.isPresent()) {
			Patient patient = optional.get();
			patient.setGender(updatedPatient.getGender());
			patient.setMobile_number(updatedPatient.getMobile_number());
			patient.setPatientAge(updatedPatient.getPatientAge());
			repository.save(patient);

			return new ResponseEntity<>(patient, HttpStatus.OK);
		} else {
			throw new PatientNotFound("Could not find Patient with patientId " + patientId);
		}
	}

	// Deletes a patient by ID
	@Override
	public ResponseEntity<String> deletePatient(int id) {
		Optional<Patient> patient = repository.findById(id);
		if (patient != null) {
			repository.deleteById(id);
			return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
		} else {
			throw new PatientNotFound("Could not find Patient with patientId " + id);
		}
	}

	// Finds patient details by patient ID
	@Override
	public ResponseEntity<Patient> findByPatientId(int id) throws PatientNotFound {
		Optional<Patient> optional = repository.findById(id);
		if (optional.isPresent()) {
			Patient patient = optional.get();
			return new ResponseEntity<>(patient, HttpStatus.OK);
		} else {
			throw new PatientNotFound("Patient not present");
		}
	}

	// Gets All patients
	@Override
	public ResponseEntity<List<Patient>> findAll() {
		return ResponseEntity.ok(repository.findAll());
	}
}