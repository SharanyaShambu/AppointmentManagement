package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.AuthRequest;
import com.entity.AuthResponseDto;
import com.entity.UserInfo;
import com.repository.UserInfoRepository;
import com.service.JwtService;
import com.service.UserService;

@RestController
@RequestMapping("/auth")
//@CrossOrigin("*")
public class AuthController {

	@Autowired
	private UserService service;
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserInfoRepository repo;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/welcome") // http://localhost:9090/auth/welcome
	public String welcome() {
		return "Welcome this endpoint is not secure";
	}

	@PostMapping("/new") // http://localhost:9090/auth/new
	public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
		return service.addUser(userInfo);
	}

	
	@PostMapping("/authenticate") // http://localhost:9090/auth/authenticatepublic
	public AuthResponseDto authenticateAndGetToken(@RequestBody AuthRequest authRequest)

	{
		
		System.out.println(authRequest.getUsername()+""+ authRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

		if (authentication.isAuthenticated())

		{

			UserInfo obj = repo.findByEmail(authRequest.getUsername()).orElse(null);

			if (obj != null)

			{

				String token = jwtService.generateToken(authRequest.getUsername(), obj.getRoles());

				return new AuthResponseDto(token, obj.getId());
			}

			else {

				throw new UsernameNotFoundException("User not found!");

			}

		}

		else {

			throw new UsernameNotFoundException("Invalid user request!");

		}

	}

	@GetMapping("/getroles/{username}") // http://localhost:9090/auth/getroles/{username}
	public String getRoles(@PathVariable String username) {
		return service.getRoles(username);
	}

	@GetMapping("/getId/{username}") // http://localhost:9090/auth/getroles/{username}
	public int getId(@PathVariable String username) {
		return service.getId(username);
	}

	@GetMapping("/getDocInfo/{id}")
	public UserInfo getDocInfo(@PathVariable int id) {
		return service.getDoctorInfo(id);
	}

	@GetMapping("/getPatInfo/{id}")
	public UserInfo getPatInfo(@PathVariable int id) {
		return service.getPatientInfo(id);
	}
}
