package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.AppointmentDto;
import com.example.demo.dto.PatientDoctorAppointmentDto;
import com.example.demo.entity.Appointment;
import com.example.demo.exception.AppointmentNotFoundException;

public interface AppointmentService {

	public ResponseEntity<PatientDoctorAppointmentDto> findById(int id) throws AppointmentNotFoundException;

	public ResponseEntity<Appointment> save(Appointment appointment, int doctorId);

	public ResponseEntity<String> deleteById(int id);

	public ResponseEntity<List<PatientDoctorAppointmentDto>> findByDoctorId(int doctorId) throws AppointmentNotFoundException;

	public ResponseEntity<String> accept(int id);

	public ResponseEntity<String> decline(int id);

	public ResponseEntity<List<PatientDoctorAppointmentDto>> findByPatientId(int patientId);

	public ResponseEntity<List<PatientDoctorAppointmentDto>> getAppointmentsForDoctor(int doctorId,LocalDate date);
	
	public ResponseEntity<List<String>> show(int patientId) ;
	
	public AppointmentDto getById(int id);
	
	public ResponseEntity<List<PatientDoctorAppointmentDto>> getAppOnStatus(int doctorId,String status);

	ResponseEntity<List<PatientDoctorAppointmentDto>> getAppOnStatusPat(int patientId, String status);
	
	public ResponseEntity<List<PatientDoctorAppointmentDto>> getTodayApp(int doctorId,String status,LocalDate date);

}
