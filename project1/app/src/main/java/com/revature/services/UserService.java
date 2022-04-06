package com.revature.services;

import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.models.User.UserRole;
import com.revature.models.UserDto;
import com.revature.repositories.UserRepository;

@Service
public class UserService {
	
	private UserRepository ur;
	
	@Autowired
	public UserService(UserRepository ur) {
		this.ur = ur;
	}
	
	public List<UserDto> getUsers() throws UserNotFoundException {
		
		return null;
	}
	
	@Transactional
	public List<UserDto> getUsersByQuery(String search, UserRole role) throws UserNotFoundException {
		
		return null;
	}
	
	public UserDto getUserById(int id) throws UserNotFoundException {
		
		return null;
	}
	
	@Transactional
	public UserDto addUser(User user) throws UserAlreadyExistsException, ValidationException {
		
		return null;
	}
	
	 @Transactional
	 public UserDto updateUser(User userUpdates) throws UserNotFoundException, ValidationException {
		 
		 return null;
	 }
	 
	 @Transactional
	 public UserDto deleteUser(int id) throws UserNotFoundException {
		 
		 return null;
	 }
	 
	 private List<User> findBySearchUsernameAndEmail(String search) {
		 
		 return null;
	 }
}





