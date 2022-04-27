package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.exceptions.ValidationException;
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
		
		User admin = new User(1, "admin", "mail@inter.net", "1234asdf", UserRole.ADMIN);
		User user = new User(2, "user", "ex@mple.com", "p4ssw0rd", UserRole.USER);
		users = new ArrayList<>();
		users.add(admin);
		users.add(user);
		
		usersDto = new ArrayList<>();
		usersDto.add(new UserDto(admin));
		usersDto.add(new UserDto(user));
	}
	
	@Test
	void getUsersTestX() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(new ArrayList<>());
		assertThrows(UserNotFoundException.class, () -> {
			us.getUsers();
		});
	}
	
	@Test
	void getUsersTest0() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(users);
		assertDoesNotThrow(() -> {
			assertEquals(usersDto, us.getUsers());
		});
	}
	
	@Test
	void getUsersByQueryTestX() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(users);
		assertThrows(UserNotFoundException.class, () -> {
			us.getUsersByQuery("    \t", UserRole.STAFF);
		});
	}
	
	@Test
	void getUsersByQueryTest0() {
		when(mockRepo.findAllByOrderByIdAsc()).thenReturn(users);
		assertDoesNotThrow(() -> {
			assertEquals(usersDto, us.getUsersByQuery(null, null));
		});
	}
	
	@Test
	void getUsersByQueryTest1() {
		when(mockRepo.findByUsernameContainingIgnoreCaseOrderByIdAsc("r.n"))
			.thenReturn(new ArrayList<>());
		when(mockRepo.findByEmailContainingIgnoreCaseOrderByIdAsc("r.n"))
			.thenReturn(users.subList(0, 1));
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.subList(0, 1), us.getUsersByQuery("r.n", UserRole.NOT_SET));
		});
	}
	
	@Test
	void getUserByIdTestX() {
		when(mockRepo.findById(0)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			us.getUserById(0);
		});
	}
	
	@Test
	void getUserByIdTest0() {
		when(mockRepo.findById(1)).thenReturn(Optional.of(users.get(0)));
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.get(0), us.getUserById(1));
		});
	}
	
	@Test
	void addUserTestX0() {
		assertThrows(ValidationException.class, () -> {
			us.addUser(null);
		});
		assertThrows(ValidationException.class, () -> {
			us.addUser(new User());
		});
	}
	
	@Test
	void addUserTestX1() {
		when(mockRepo.existsUserByUsername("admin")).thenReturn(true);
		when(mockRepo.existsUserByEmail("mail@inter.net")).thenReturn(true);
		assertThrows(UserAlreadyExistsException.class, () -> {
			us.addUser(users.get(0));
		});
	}
	
	@Test
	void addUserTest0() {
		User u = new User(-1, "noob", "git@gud.org", "5crubl0rd", UserRole.USER);
		User up = new User(3, "noob", "git@gud.org", "5crubl0rd", UserRole.USER);
		UserDto ud = new UserDto(up);
		
		when(mockRepo.existsUserByUsername("admin")).thenReturn(false);
		when(mockRepo.existsUserByEmail("mail@inter.net")).thenReturn(false);
		when(mockRepo.save(u)).thenReturn(up);
		assertDoesNotThrow(() -> {
			assertEquals(ud, us.addUser(u));
		});
	}
	
	@Test
	void updateUserPasswordTestX0() {
		when(mockRepo.findById(0)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			us.updateUserPassword(0, "cooltimes3");
		});
	}
	
	@Test
	void updateUserPasswordTestX2() {
		when(mockRepo.findById(1)).thenReturn(Optional.of(users.get(0)));
		assertThrows(ValidationException.class, () -> {
			us.updateUserPassword(1, null);
		});
	}
	
	@Test
	void updateUserPasswordTest0() {
		when(mockRepo.findById(2)).thenReturn(Optional.of(users.get(1)));
		when(mockRepo.save(users.get(1))).thenReturn(users.get(1));
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.get(1), us.updateUserPassword(2, "cooltimes3"));
		});
	}
	
	@Test
	void updateUserRoleTestX0() {
		when(mockRepo.findById(0)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			us.updateUserRole(0, UserRole.STAFF);
		});
	}
	
	@Test
	void updateUserRoleTestX1() {
		when(mockRepo.findById(2)).thenReturn(Optional.of(users.get(1)));
		assertThrows(ValidationException.class, () -> {
			us.updateUserRole(2, null);
		});
		assertThrows(ValidationException.class, () -> {
			us.updateUserRole(2, UserRole.NOT_SET);
		});
	}
	
	@Test
	void updateUserRoleTest0() {
		User u = new User(2, "user", "ex@mple.com", "p4ssw0rd", UserRole.USER);
		User up = new User(2, "user", "ex@mple.com", "p4ssw0rd", UserRole.STAFF);
		UserDto ud = new UserDto(up);
		
		when(mockRepo.findById(2)).thenReturn(Optional.of(u));
		when(mockRepo.save(up)).thenReturn(up);
		assertDoesNotThrow(() -> {
			assertEquals(ud, us.updateUserRole(2, UserRole.STAFF));
		});
	}
	
	@Test
	void deleteUserTestX() {
		when(mockRepo.findById(0)).thenReturn(Optional.empty());
		assertThrows(UserNotFoundException.class, () -> {
			us.deleteUser(0);
		});
	}
	
	@Test
	void deleteUserTest0() {
		when(mockRepo.findById(1)).thenReturn(Optional.of(users.get(0)));
		assertDoesNotThrow(() -> {
			assertEquals(usersDto.get(0), us.deleteUser(1));
		});
	}
}




