package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Doctor;
import com.example.demo.exception.SpecializationNotFound;

public interface DoctorService {

	public ResponseEntity<String> addDoctor(int id,Doctor doctor);

	public ResponseEntity<String> deleteDoctor(int doctorId);

	public ResponseEntity<Doctor> updateDoctor(int doctorId, Doctor doctor);

	public ResponseEntity<List<Doctor>> findBySpecialization(String specialization) throws SpecializationNotFound ;

	public ResponseEntity<Doctor> findById(int doctorId);
}
