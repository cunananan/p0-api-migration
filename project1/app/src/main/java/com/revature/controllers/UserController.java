package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.AccessDeniedException;
import com.revature.models.User;
import com.revature.models.User.UserRole;
import com.revature.models.UserDto;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import com.revature.util.ValidationUtil;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService us;
	private AuthService as;
	
	@Autowired
	public UserController(UserService us, AuthService as) {
		super();
		this.us = us;
		this.as = as;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDto>> getUsers(@RequestHeader(name="Authorization", required=false) String token,
			                                      @RequestParam(name="search", required=false) String searchStr,
	                                              @RequestParam(name="role", required=false) String roleStr)
	{
		List<UserDto> users;
		// If user is not an admin, only show self
		if (!as.authorizeRole(token, UserRole.ADMIN)) {
			users = new ArrayList<>();
			users.add(us.getUserById(as.extractIdFromToken(token)));
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		
		UserRole role = null;
		boolean queriesAllNull = true;
		
		if (!StringUtils.isBlank(searchStr)) {
			queriesAllNull = false;
		}
		if (roleStr != null) {
			role = userRoleFromString(roleStr);
			if (role != UserRole.NOT_SET) queriesAllNull = false; 
		}
		// May throw runtime exceptions; handled by GlobalExceptionHandler
		users = (queriesAllNull) ? us.getUsers() : us.getUsersByQuery(searchStr, role);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@RequestHeader(name="Authorization", required=false) String token,
	                                           @PathVariable("id") int id)
	{
		// Only admins and the id's user can view this
		if (!as.authorizeRole(token, UserRole.ADMIN) && !as.authorizeUser(token, id)) {
			// TODO Log
			throw new AccessDeniedException("Not authorized to view user");
		}
		return new ResponseEntity<>(us.getUserById(id), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestHeader(name="Authorization", required=false) String token,
	                                         @RequestBody User newUser)
	{
		// If user is not an admin, can only add a user with the USER role
		if (StringUtils.isBlank(token) || !as.authorizeRole(token, UserRole.ADMIN)) {
			newUser.setRole(UserRole.USER);
		}
		UserDto user = us.addUser(newUser);
		return new ResponseEntity<>("New user \"" + user.username + "\" was added at index " + user.id, HttpStatus.CREATED);
	}
	
	// TODO Break up this method
	@PutMapping("/{id}")
	public ResponseEntity<String> updateUser(@RequestHeader(name="Authorization", required=false) String token,
	                                         @PathVariable("id") int id,
	                                         @RequestParam(name="currentPassword", required=false) String currPass,
	                                         @RequestParam(name="newPassword", required=false) String newPass,
	                                         @RequestParam(name="newRole", required=false) String newRole)
	{
		// Only admins and the id's user can view this
		if (!as.authorizeRole(token, UserRole.ADMIN) && !as.authorizeUser(token, id)) {
			// TODO Log
			throw new AccessDeniedException("Not authorized to modify user");
		}
		// Admin can change user role and password without confirmation
		if (as.authorizeRole(token, UserRole.ADMIN)) {
			String message = "";
			int numUpdates = 0;
			boolean newPassInvalid = false;
			if (!StringUtils.isBlank(newRole)) {
				UserRole role = userRoleFromString(newRole);
				if (role != UserRole.NOT_SET) {
					us.updateUserRole(id, role);
					message += "Role ";
					numUpdates++;
				}
			}
			if (!StringUtils.isBlank(newPass)) {
				if (ValidationUtil.validatePassword(newPass)) {
					us.updateUserPassword(id, newPass);
					message += (numUpdates == 0) ? "Password " : "and password ";
					numUpdates++;
				} else {
					newPassInvalid = true;
				}
			}
			message += (numUpdates == 0) ? "No fields " : "";
			message += (numUpdates == 1) ? "was updated" : "were updated";
			message += (newPassInvalid) ? "; new password was invalid" : "";
			return new ResponseEntity<>(message, (numUpdates > 0) ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST);
		}
		// User can change own password, but must provide current password first
		else if (as.authorizeUser(token, id)) {
			if (StringUtils.isBlank(currPass)) {
				if (StringUtils.isBlank(newPass)) {
					return new ResponseEntity<>("No fields were updated", HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<>("Current password was not provided", HttpStatus.BAD_REQUEST);
			}
			if (!as.verifyPassword(token, currPass)) {
				return new ResponseEntity<>("Current password is incorrect", HttpStatus.BAD_REQUEST);
			}
			if (StringUtils.isBlank(newPass)) {
				return new ResponseEntity<>("New password was not provided", HttpStatus.BAD_REQUEST);
			}
			if (!ValidationUtil.validatePassword(newPass)) {
				return new ResponseEntity<>("New password is invalid", HttpStatus.BAD_REQUEST);
			}
			us.updateUserPassword(id, newPass);
			return new ResponseEntity<>("Password was updated", HttpStatus.CREATED);
		}
		return new ResponseEntity<>("No fields were updated", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@RequestHeader(name="Authorization", required=false) String token,
	                                         @PathVariable("id") int id)
	{
		
		if (!as.authorizeRole(token, UserRole.ADMIN)) {
			throw new AccessDeniedException("Not authorized to delete users");
		}
		UserDto user = us.deleteUser(id);
		return new ResponseEntity<>("Deleted user \"" + user.username + "\"", HttpStatus.OK);
	}
	
	
	private UserRole userRoleFromString(String roleStr) {
		try {
			return UserRole.valueOf(roleStr.toUpperCase());
		} catch (IllegalArgumentException e) {
			// TODO Logging
			return UserRole.NOT_SET;
		}
	}
}





