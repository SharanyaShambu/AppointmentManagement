package com.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.entity.UserInfo;
import com.repository.UserInfoRepository;

@Service
public class UserService {
	@Autowired
	private UserInfoRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<?> addUser(@RequestBody UserInfo userInfo) {
	    String name = userInfo.getEmail();
	    Optional<UserInfo> existingUser = repository.findByEmail(name);

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    if (!existingUser.isPresent()) {
	        // Encrypt the password
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));

	        // Save the new user
	        UserInfo savedUser = repository.save(userInfo);

	        // Prepare the response body
	        Map<String, Object> responseBody = new HashMap<>();
	        responseBody.put("id", savedUser.getId());
	        responseBody.put("message", "Registration successful.");

	        // Return 201 Created with user info and headers
	        return new ResponseEntity<>(responseBody, headers, HttpStatus.CREATED);
	    } else {
	        // User already exists
	        Map<String, String> errorBody = new HashMap<>();
	        errorBody.put("error", "This username is already registered.");

	        // Return 409 Conflict with error message and headers
	        return new ResponseEntity<>(errorBody, headers, HttpStatus.CONFLICT);
	    }
	}


	public String getRoles(String username) {
		UserInfo obj2 = repository.findByEmail(username).orElse(null);
		if (obj2 != null) {
			return obj2.getRoles();
		}
		return "Not Found";
	}
	
	public int getId(String username) {
		UserInfo obj2 = repository.findByEmail(username).orElse(null);
		if (obj2 != null) {
			return obj2.getId();
		}
		return 0;
	}

	public UserInfo getDoctorInfo(int id) {
		return repository.findById(id).get();
	}

	public UserInfo getPatientInfo(int id) {
		return repository.findById(id).get();
	}

}
