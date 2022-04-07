package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	List<User> findAllByOrderByIdAsc();
	
	List<User> findByUsernameContainingIgnoreCaseOrderByIdAsc(String search);
	
	List<User> findByEmailContainingIgnoreCaseOrderByIdAsc(String search);
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	
	boolean existsUserByUsername(String username);
	
	boolean existsUserByEmail(String email);
}
