package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.client.DocClient;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Doctor;
import com.example.demo.exception.DoctorNotFound;
import com.example.demo.exception.SpecializationNotFound;
import com.example.demo.repository.DoctorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

	private final DoctorRepository repo;
	private final DocClient client;
	private static final Logger logger = LoggerFactory.getLogger(DoctorServiceImpl.class);

	// Add a new doctor to the repository
	@Override
	public ResponseEntity<String> addDoctor(int id,Doctor doctor) {
		doctor.setDoctorId(id);
		UserDto dto=client.getDocInfo(id);
		doctor.setEmailId(dto.getEmail());
		doctor.setName(dto.getName());
		repo.save(doctor);
		return new ResponseEntity<>("Doctor added successfully", HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<String> deleteDoctor(int doctorId) throws DoctorNotFound {
		Optional<Doctor> doctor = repo.findById(doctorId);
		if (doctor.isPresent()) {
			repo.deleteById(doctorId);
			logger.info("Doctor with id {} deleted successfully", doctorId);
			return new ResponseEntity<>("Doctor with id " + doctorId + " deleted successfully", HttpStatus.OK);
		} else {
			logger.error("Doctor with id {} not found", doctorId);
			throw new DoctorNotFound("Doctor with id " + doctorId + " not found");
		}
	}


	// Update an existing doctor's details
	@Override
	public ResponseEntity<Doctor> updateDoctor(int doctorId, Doctor updatedDoctor) throws DoctorNotFound {
		Optional<Doctor> optional = repo.findById(doctorId);
		if (optional.isPresent()) {
			Doctor doctor = optional.get();
			doctor.setMobile_number(updatedDoctor.getMobile_number());
			doctor.setSpecialization(updatedDoctor.getSpecialization());
			doctor.setGender(updatedDoctor.getGender());
			repo.save(doctor);
			return new ResponseEntity<>(doctor, HttpStatus.OK);
		} else {
			throw new DoctorNotFound("Could not find Doctor with doctorId " + doctorId);
		}
	}

	// Find doctors by their specialization
	@Override
	public ResponseEntity<List<Doctor>> findBySpecialization(String specialization) throws SpecializationNotFound {
		List<Doctor> doctors = repo.findBySpecialization(specialization);
		if (!doctors.isEmpty()) {
			return new ResponseEntity<>(doctors, HttpStatus.OK);
		} else {
			throw new SpecializationNotFound("Specialization is not found");
		}
	}

	// Find a doctor by their ID
	public ResponseEntity<Doctor> findById(int doctorId) throws DoctorNotFound {
		Optional<Doctor> optional = repo.findById(doctorId);
		if (optional.isPresent()) {
			Doctor doctor = optional.get();
			return new ResponseEntity<>(doctor, HttpStatus.OK);
		} else {
			throw new DoctorNotFound("Doctor not present");
		}
	}
	
	public ResponseEntity<List<Doctor>> getAll(){
		return new ResponseEntity<List<Doctor>>(repo.findAll(),HttpStatus.OK);
	}
}
