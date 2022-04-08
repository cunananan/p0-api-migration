package com.revature.models;

import java.util.Objects;

import com.revature.models.User.UserRole;
import com.revature.util.ValidationUtil;

public class UserDto {
	
	public int id;
	public String username;
	public String email;
	public UserRole role;
	
	public UserDto() {
		super();
		id = -1;
		username = null;
		email = null;
		role = null;
	}
	
	public UserDto(User source) {
		super();
		copyFrom(source);
	}
	
	public void copyFrom(User source) {
		id = source.getId();
		username = source.getUsername();
		email = source.getEmail();
		role = source.getRole();
	}

	public void copyTo(User target) {
		if (id >= 0) target.setId(id);
		if (ValidationUtil.validateUsername(username)) target.setUsername(username);
		if (ValidationUtil.validateEmail(email)) target.setEmail(email);
		if (role != null && role != UserRole.NOT_SET) target.setRole(role);
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", email=" + email + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDto other = (UserDto) obj;
		return Objects.equals(email, other.email) && id == other.id && role == other.role
				&& Objects.equals(username, other.username);
	}
}
