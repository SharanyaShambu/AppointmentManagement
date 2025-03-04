package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indicates that this class is a JPA entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "emailId" })) // Specifies a unique constraint on the
																			// emailId column
@Data // Lombok annotation to generate getters, setters, toString, equals, and
		// hashCode methods
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields as parameters
public class Doctor {

	@Id // Specifies the primary key of the entity
	private int doctorId; // Unique identifier for the doctor

	@NotBlank(message = "Email ID cannot be blank") // Validation to ensure email ID is not blank
	@Email(message = "Email ID must be valid") // Validation to ensure email ID is valid
	private String emailId; // Email ID of the doctor

	@NotBlank(message = "Name cannot be blank") // Validation to ensure name is not blank
	private String name; // Name of the doctor

	@NotNull(message = "Mobile number cannot be null") // Validation to ensure mobile number is not null
	@Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits long") // Validation to ensure mobile
																						// number is 10 digits long
	private String mobile_number; // Mobile number of the doctor

	@NotBlank(message = "Specialization cannot be blank") // Validation to ensure specialization is not blank
	private String specialization; // Specialization of the doctor
	
	@NotBlank(message="Choose One")
	private String gender;
}
