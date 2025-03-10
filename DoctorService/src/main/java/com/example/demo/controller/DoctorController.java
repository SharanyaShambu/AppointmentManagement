package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Doctor;
import com.example.demo.exception.SpecializationNotFound;
import com.example.demo.service.DoctorServiceImpl;

import lombok.AllArgsConstructor;

@RestController // Indicates that this class is a REST controller
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields as parameters
@RequestMapping("/doctor") // Base URL for all endpoints in this controller
// @CrossOrigin("*")
public class DoctorController {

	private static final Logger logger = LoggerFactory.getLogger(DoctorController.class); // Logger instance

	private final DoctorServiceImpl rser; // Service layer for doctor-related operations

	@PostMapping("/add/{id}") // Endpoint to add a new doctor
	public ResponseEntity<String> addDoctor(@PathVariable int id,@RequestBody Doctor doctor) {
		logger.info("Adding new doctor"); // Log message
		return rser.addDoctor(id,doctor); // Call service layer to add doctor
	}

	@DeleteMapping("/delete/{doctorId}") // Endpoint to delete a doctor by ID
	public ResponseEntity<String> deleteDoctor(@PathVariable int doctorId) {
		logger.info("Deleting doctor with ID: {}", doctorId); // Log message
		return rser.deleteDoctor(doctorId); // Call service layer to delete doctor
	}

	@PatchMapping("/update/{doctorId}") // Endpoint to update doctor details by ID
	public ResponseEntity<Doctor> updateDoctor(@PathVariable int doctorId, @RequestBody Doctor updatedDoctor) {
		logger.info("Updating doctor details for ID: {}", doctorId); // Log message
		return rser.updateDoctor(doctorId, updatedDoctor); // Call service layer to update doctor
	}

	@GetMapping("/listOfDoctorsBySpec/{specialization}") // Endpoint to get doctors by specialization
	public ResponseEntity<List<Doctor>> findBySpecialization(@PathVariable String specialization) throws SpecializationNotFound {
		logger.info("Fetching doctors with specialization: {}", specialization); // Log message
		return rser.findBySpecialization(specialization); // Call service layer to get doctors by specialization
	}

	@GetMapping("/findbyId/{doctorId}") // Endpoint to get doctor details by ID
	public ResponseEntity<Doctor> getDoctorById(@PathVariable int doctorId) {
		logger.info("Fetching doctor details for ID: {}", doctorId); // Log message
		return rser.findById(doctorId); // Call service layer to get doctor details by ID
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<Doctor>> getAll(){
		return rser.getAll();
	}
}
