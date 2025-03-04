package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.UserDto;

@FeignClient("SECURITY-SERVICE")
public interface PatClient {
	
	@GetMapping("auth/getPatInfo/{id}")
	public UserDto getPatInfo(@PathVariable int id);
	

}
