package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.client.AppointmentClient;
import com.example.demo.dto.AppointmentDto;
import com.example.demo.entity.Medicine;
import com.example.demo.entity.Prescription;
import com.example.demo.exception.AppointmentNotFoundException;
import com.example.demo.exception.PrescriptionNotFound;
import com.example.demo.repository.MedicineRepository;
import com.example.demo.repository.PrescriptionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

	private static final Logger logger = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

	private MedicineRepository medRepo;

	private PrescriptionRepository presRepo;

	private AppointmentClient client;

	@Override
	public Medicine addMedicine(Medicine medicine, int aid) {
		logger.debug("Adding medicine with PID: {}", aid);
		medicine.setAid(aid);
		Medicine savedMedicine = medRepo.save(medicine);
		logger.debug("Saved medicine: {}", savedMedicine);
		return savedMedicine;
	}

	/**
	 * Finds medicines by prescription ID (pid).
	 * 
	 * @param pid the prescription ID
	 * @return list of medicines
	 */
	public ResponseEntity<List<Medicine>> findMedicneById(int aid) {
		List<Medicine> medicines=  medRepo.findByAid(aid);
		return new ResponseEntity<>(medicines,HttpStatus.OK);
	}

	/**
	 * Adds a prescription with medicines based on appointment ID.
	 * 
	 * @param aid          the appointment ID
	 * @param prescription the prescription entity
	 * @return the saved prescription entity wrapped in ResponseEntity
	 */
	public ResponseEntity<Prescription> addPrescription(int aid) {
		AppointmentDto app = client.getById(aid).getBody();
		Prescription prescription=new Prescription();
		if (app != null) {
			prescription.setAid(aid);
			prescription.setDoctorid(app.getDoctorId());
			prescription.setPatientid(app.getPatientId());
			List<Medicine> medicine = medRepo.findByAid(aid);
			prescription.setMedicine(medicine);
			prescription.setDate(LocalDate.now());
			return ResponseEntity.ok(presRepo.save(prescription));
		} else {
			throw new AppointmentNotFoundException("Appointment with" + aid + "is not found");
		}
	}

	/**
	 * Deletes a prescription by ID.
	 * 
	 * @param pid the prescription ID
	 * @return a success message wrapped in ResponseEntity
	 */
	public ResponseEntity<String> deletePrescription(int aid) {
		presRepo.deleteById(aid);
		return ResponseEntity.ok("Deleted Successfully");
	}

	/**
	 * Finds a prescription by patient ID.
	 * 
	 * @param patientId the patient ID
	 * @return the found prescription entity wrapped in ResponseEntity
	 * @throws PrescriptionNotFound if no prescription found for given patient ID
	 */
	public ResponseEntity<Prescription> findByPatientId(int patientId) throws PrescriptionNotFound {
		Prescription prescription = presRepo.findByPatientid(patientId);
		if (prescription!=null) {
			return ResponseEntity.ok(prescription);
		} else {
			throw new PrescriptionNotFound("Prescription of id " + patientId +" not found");
		}
	}

	/**
	 * Finds a prescription by appointment ID.
	 * 
	 * @param aid the appointment ID
	 * @return the found prescription entity wrapped in ResponseEntity
	 * @throws PrescriptionNotFound if no prescription found for given appointment
	 *                              ID
	 */
	public ResponseEntity<Prescription> findByAid(int aid) throws PrescriptionNotFound {
		Prescription pres = presRepo.findByAid(aid);
		if (pres!=null) {
			return ResponseEntity.ok(pres);
		} else {
			throw new PrescriptionNotFound("Prescription of id " + aid);
		}
	}
}
