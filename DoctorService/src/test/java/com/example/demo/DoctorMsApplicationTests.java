package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Doctor;
import com.example.demo.exception.DoctorNotFound;
import com.example.demo.exception.SpecializationNotFound;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.service.DoctorServiceImpl;

@SpringBootTest
public class DoctorMsApplicationTests {

	@Mock
	private DoctorRepository repo;

	@InjectMocks
	private DoctorServiceImpl doctorService;

	private Doctor doctor;

	@BeforeEach
	void setUp() {
		doctor = new Doctor(1, "doctor@example.com", "Dr. Smith", "0987654321", "Cardiology","Male");
	}

	@Test
	void testAddDoctor() {
		when(repo.save(doctor)).thenReturn(doctor);
		ResponseEntity<String> response = doctorService.addDoctor(1, doctor);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("Doctor added successfully", response.getBody());
	}

	@Test
	void testDeleteDoctor() throws DoctorNotFound {
		when(repo.findById(1)).thenReturn(Optional.of(doctor));
		doNothing().when(repo).deleteById(1);

		ResponseEntity<String> response = doctorService.deleteDoctor(1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Doctor with id 1 deleted successfully", response.getBody());

		verify(repo, times(1)).deleteById(1);
	}

	@Test
	void testUpdateDoctor() throws DoctorNotFound {
		Doctor updatedDoctor = new Doctor(1, "newdoctor@example.com", "Dr. Jane", "1234567890", "Dermatology","Male");
		when(repo.findById(1)).thenReturn(Optional.of(doctor));

		ResponseEntity<Doctor> response = doctorService.updateDoctor(1, updatedDoctor);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(updatedDoctor.getEmailId(), response.getBody().getEmailId());
		assertEquals(updatedDoctor.getName(), response.getBody().getName());
		assertEquals(updatedDoctor.getGender(),response.getBody().getGender());
	}

	@Test
	void testFindBySpecialization() throws SpecializationNotFound {
		List<Doctor> doctors = List.of(doctor);
		when(repo.findBySpecialization("Cardiology")).thenReturn(doctors);
		ResponseEntity<List<Doctor>> response = doctorService.findBySpecialization("Cardiology");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(doctors, response.getBody());
	}

	@Test
	void testFindById() throws DoctorNotFound {
		when(repo.findById(1)).thenReturn(Optional.of(doctor));
		ResponseEntity<Doctor> response = doctorService.findById(1);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(doctor, response.getBody());
	}

}
