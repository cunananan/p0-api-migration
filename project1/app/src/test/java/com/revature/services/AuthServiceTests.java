package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.models.User;
import com.revature.models.User.UserRole;
import com.revature.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {
	
	private static UserRepository mockRepo;
	private static AuthService as;
	private static User admin;
	private static User user;
	private static String adminToken;
	private static String userToken;
	
	@BeforeAll
	public static void setup() {
		mockRepo = mock(UserRepository.class);
		as = new AuthService(mockRepo);
		admin = new User(1, "admin", "mail@inter.net", "1234", UserRole.ADMIN);
		user = new User(2, "user", "ex@mple.com", "pass", UserRole.USER);
		
		adminToken = "1:ADMIN";
		userToken = "2:USER";
	}
	
	@Test
	void loginTestX0() {
		assertThrows(AuthenticationException.class, () -> {
			as.login(null, "1234");
		});
		assertThrows(AuthenticationException.class, () -> {
			as.login("", "1234");
		});
	}
	
	@Test
	void loginTestX1() {
		
		assertThrows(AuthenticationException.class, () -> {
			as.login("admin", null);
		});
		assertThrows(AuthenticationException.class, () -> {
			as.login("admin", "");
		});
	}
	
	@Test
	void loginTest0() {
		when(mockRepo.findByUsernameOrEmail("admin", "admin")).thenReturn(Optional.of(admin));
		assertDoesNotThrow(() -> {
			assertEquals(adminToken, as.login("admin", "1234"));
		});
	}
	
	@Test
	void loginTest1() {
		when(mockRepo.findByUsernameOrEmail("ex@mple.com", "ex@mple.com")).thenReturn(Optional.of(user));
		assertDoesNotThrow(() -> {
			assertEquals(userToken, as.login("ex@mple.com", "pass"));
		});
	}
	
	@Test
	void authorizeUserTestX0() {
		assertThrows(AuthorizationException.class, () -> {
			as.authorizeUser(null, 1);
		});
	}
	
	@Test
	void authorizeUserTestX1() {
		assertThrows(AuthorizationException.class, () -> {
			as.authorizeUser(userToken, -1);
		});
	}
	
	@Test
	void authorizeUserTest0() {
		assertDoesNotThrow(() -> {
			assertEquals(true, as.authorizeUser(adminToken, 1));			
		});
	}
	
	@Test
	void authorizeRoleTestX0() {
		assertThrows(AuthorizationException.class, () -> {
			as.authorizeRole(null, UserRole.USER);
		});
	}
	
	@Test
	void authorizeRoleTestX1() {
		assertThrows(AuthorizationException.class, () -> {
			as.authorizeRole(userToken, UserRole.STAFF, UserRole.ADMIN);
		});
	}
	
	@Test
	void authorizeRoleTest0() {
		assertDoesNotThrow(() -> {
			assertEquals(true, as.authorizeRole(userToken));
		});
	}
	
	@Test
	void authorizeRoleTest1() {
		assertDoesNotThrow(() -> {
			assertEquals(true, as.authorizeRole(adminToken, UserRole.ADMIN));			
		});
	}
}





