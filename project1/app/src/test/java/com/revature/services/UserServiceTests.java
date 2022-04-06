package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ValidationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.models.User.UserRole;
import com.revature.models.UserDto;
import com.revature.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
	
	private static UserRepository mockRepo;
	private static UserService us;
	private static List<User> users;
	private static List<UserDto> usersDto;
	
	@BeforeAll
	public static void setup() {
		mockRepo = mock(UserRepository.class);
		us = new UserService(mockRepo);
		
		User admin = new User(1, "admin", "mail@inter.net", "1234", UserRole.ADMIN);
		User user = new User(2, "user", "ex@mple.com", "pass", UserRole.USER);
		users = new ArrayList<>();
		users.add(admin);
		users.add(user);
		
		usersDto = new ArrayList<>();
		usersDto.add(new UserDto(admin));
		usersDto.add(new UserDto(user));
	}
	
	@Test
	void getUsersTestX() {
		
		assertThrows(UserNotFoundException.class, () -> {
			us.getUsers();
		});
	}
	
	@Test
	void getUsersTest0() {
		
		assertDoesNotThrow(() -> {
			assertEquals(usersDto, us.getUsers());
		});
	}
	
	@Test
	void getUsersByQueryTestX() {
		
		assertThrows(UserNotFoundException.class, () -> {
			us.getUsersByQuery("    \t", UserRole.STAFF);
		});
	}
	
	@Test
	void getUsersByQueryTest0() {
		
		assertDoesNotThrow(() -> {
			assertEquals(usersDto, us.getUsersByQuery(null, null));
		});
	}
	
	@Test
	void getUsersByQueryTest1() {
		
		
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.subList(0, 1), us.getUsersByQuery("r.n", UserRole.NOT_SET));
		});
	}
	
	@Test
	void getUserByIdTestX() {
		
		assertThrows(UserNotFoundException.class, () -> {
			us.getUserById(0);
		});
	}
	
	@Test
	void getUserByIdTest0() {
		
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.get(0), us.getUserById(1));
		});
	}
	
	@Test
	void addUserTestX1() {
		
		assertThrows(UserAlreadyExistsException.class, () -> {
			us.addUser(users.get(0));
		});
	}
	
	@Test
	void addUserTestX0() {
		assertThrows(ValidationException.class, () -> {
			us.addUser(new User());
		});
	}
	
	@Test
	void addUserTest0() {
		
		assertDoesNotThrow(() -> {
			//assertEquals(, us.addUser());
		});
	}
	
	@Test
	void updateUserTestX0() {
		assertThrows(UserNotFoundException.class, () -> {
			us.updateUser(null);
		});
	}
	
	@Test
	void updateUserTestX1() {
		
		assertThrows(UserNotFoundException.class, () -> {
			us.updateUser(new User(-1, "u123454", "e@mail.com", "password", UserRole.USER));
		});
	}
	
	@Test
	void updateUserTestX2() {
		assertThrows(ValidationException.class, () -> {
			us.updateUser(new User(1, null, null, null, null));
		});
	}
	
	@Test
	void updateUserTest0() {
		
		assertDoesNotThrow(() -> {
			//assertEquals(, us.updateUser());
		});
	}
	
	@Test
	void deleteUserTestX() {
		
		assertThrows(UserNotFoundException.class, () -> {
			us.deleteUser(0);
		});
	}
	
	@Test
	void deleteUserTest0() {
		
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.get(0), us.deleteUser(1));
		});
	}
}




