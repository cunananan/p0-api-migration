package com.revature.services;

import org.apache.commons.lang3.StringUtils;
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
	public String login(String user, String password) {
		if (user == null || StringUtils.isBlank(user)) {
			throw new AuthenticationException("Username or email was not provided");
		}
		if (password == null || StringUtils.isBlank(password)) {
			throw new AuthenticationException("Password was not provided");
		}
		User userObj = ur.findByUsernameOrEmail(user, user)
		                 .orElseThrow(() -> 
		                	 new AuthenticationException("Could not find user with username or email of: " + user)
		                 );
		// TODO hash password here
		if (!userObj.getPassword().equals(password)) {
			throw new AuthenticationException("Incorrect password");
		}
		
		return generateToken(userObj);
	}
	
	public boolean authorizeUser(String token, int userId) {
		if (token == null || StringUtils.isBlank(token)) {
			throw new AuthorizationException("Null token");
		}
		if (tokenIsExpired(token)) {
			throw new AuthorizationException("Expired token");
		}
		if (userId != extractIdFromToken(token)) {
			throw new AuthorizationException("Invalid token");
		}
		return true;
	}
	
	public boolean authorizeRole(String token, UserRole... roles) {
		// Allow authorization by default if no roles are specified
		if (roles.length <= 0) {
			return true;
		}
		if (token == null || StringUtils.isBlank(token)) {
			throw new AuthorizationException("Null token");
		}
		if (tokenIsExpired(token)) {
			throw new AuthorizationException("Expired token");
		}
		UserRole role = extractRoleFromToken(token);
		for (UserRole r : roles) {
			if (role == r || r == UserRole.NOT_SET) {
				return true;
			}
		}
		throw new AuthorizationException("Invalid token");
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
		
		return UserRole.valueOf(splitToken[1]);
	}
	
	private boolean tokenIsExpired(String token) {
		
		// TODO replace this with JWT implementation
		
		return false;
	}
}
