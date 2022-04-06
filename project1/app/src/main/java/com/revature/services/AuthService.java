package com.revature.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.models.User;
import com.revature.models.User.UserRole;
import com.revature.repositories.UserRepository;

@Service
public class AuthService {
	
	private UserRepository ur;
	
	@Autowired
	public AuthService(UserRepository ur) {
		this.ur = ur;
	}
	
	// Returns token as string iff successful
	public String login(String user, String password) throws AuthenticationException {
		
		return null;
	}
	
	public boolean authorizeUser(String token, int userId) throws AuthorizationException {
		
		return true;
	}
	
	public boolean authorizeRole(String token, UserRole... roles) throws AuthorizationException {
		
		return true;
	}
	
	
	private String generateToken(User user) {
		if (user == null) return null;
		
		// TODO replace this with JWT implementation
		return user.getId() + ":" + user.getRole().toString();
	}
	
	private int extractIdFromToken(String token) {
		
		// TODO replace this with JWT implementation
		String[] splitToken = token.split(":");
		
		return Integer.parseInt(splitToken[0]);
	}
	
	private UserRole extractRoleFromToken(String token) {
		
		// TODO replace this with JWT implementation
		String[] splitToken = token.split(":");
		
		return UserRole.valueOf(splitToken[0]);
	}
	
	private boolean tokenNotExpired(String token) {
		
		// TODO replace this with JWT implementation
		
		return true;
	}
}
