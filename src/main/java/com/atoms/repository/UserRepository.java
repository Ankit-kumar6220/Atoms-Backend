package com.atoms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atoms.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{

     public User findByUsername(String username);

	
}
