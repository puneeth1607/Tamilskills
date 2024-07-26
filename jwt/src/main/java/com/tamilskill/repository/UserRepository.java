package com.tamilskill.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tamilskill.model.User;
import java.util.List;


@Repository
public interface UserRepository  extends JpaRepository<User,Long>{

	Optional<User> findByUsername(String username);
	
 // to check weather the user is exist or not
	Boolean existsByUsername(String name);
	
	
}
