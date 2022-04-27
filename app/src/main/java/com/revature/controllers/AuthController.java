package com.revature.controllers;

import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private AuthService as;
	
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public AuthController(AuthService as) {
		super();
		this.as = as;
	}
	
	@PostMapping
	public ResponseEntity<String> login(@RequestParam(required=false) String username,
	                                    @RequestParam(required=false) String password)
	{
		MDC.put("requestId", UUID.randomUUID().toString());
		// Login will throw runtime exceptions if credentials are bad
		String token = as.login(username, password);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", token);
		
		LOG.info("User `" + username + "` logged in successfully");
		return new ResponseEntity<>("Login successful.", headers, HttpStatus.OK);
	}
}
