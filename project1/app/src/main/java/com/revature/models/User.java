package com.revature.models;

import static com.revature.util.ValidationUtil.validateUsername;
import static com.revature.util.ValidationUtil.validateEmail;
import static com.revature.util.ValidationUtil.validatePassword;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false, unique=true)
	private String username;
	@Column(nullable=false, unique=true)
	private String email;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	public enum UserRole {
		NOT_SET,
		USER,
		STAFF,
		ADMIN
	}
	
	// Constructors
	public User() {
		super();
		username = null;
		email = null;
		password = null;
		role = null;
	}
	public User(int id, String username, String email, String password, UserRole role) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	public User(User other) {
		super();
		id = other.id;
		username = other.username;
		email = other.email;
		password = other.password;
		role = other.role;
	}

	// Setters and Getters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = Math.max(0, id);
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		if (username != null) this.username = username;
	}
	public String getEmail() {
		return email;
	}	
	public void setEmail(String email) {
		if (email != null) this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if (password != null) this.password = password;
	}
	public UserRole getRole() {
		return role;
	}	
	public void setRole(UserRole role) {
		if (role != null && role != UserRole.NOT_SET) this.role = role;
	}
//	
//	public void copyTo(User target) {
//		if (id >= 0) target.setId(id);
//		if (validateUsername(username)) target.setUsername(username);
//		if (validateEmail(email)) target.setEmail(email);
//		if (validatePassword(password)) target.setPassword(password);
//		if (role != null) target.setRole(role);
//	}
	
	public boolean validate() {
		id = Math.max(0, id);
		if (role == null) role = UserRole.USER;
		return validateUsername(username) && validateEmail(email) && validatePassword(password);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && id == other.id && Objects.equals(password, other.password)
				&& role == other.role && Objects.equals(username, other.username);
	}
}
