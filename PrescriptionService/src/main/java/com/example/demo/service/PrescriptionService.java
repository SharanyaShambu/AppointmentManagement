package com.example.demo.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Medicine;
import com.example.demo.entity.Prescription;
import com.example.demo.exception.PrescriptionNotFound;

public interface PrescriptionService {
	
	public Medicine addMedicine(Medicine medicines, int pid);
	
	public ResponseEntity<Prescription> addPrescription(int aid);
	
	public ResponseEntity<String> deletePrescription(int pid);
	
	public ResponseEntity<Prescription> findByPatientId(int patientId) throws PrescriptionNotFound;
	
	public ResponseEntity<Prescription> findByAid(int aid) throws PrescriptionNotFound;
	
	public ResponseEntity<List<Medicine>> findMedicneById(int aid) ;


}
