package com.atoms;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.atoms.model.Role;
import com.atoms.model.User;
import com.atoms.model.UserRole;
import com.atoms.service.UserService;

@SpringBootApplication
public class AtomsApplication  {

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(AtomsApplication.class, args);
		
		System.out.println("Starting code");

		
	}

//	//CommandLineRunner gives a area where we can run 
//	@Override
//	public void run(String... args) throws Exception {
//		
//		System.out.println("Starting code");

	
//		 User user = new User(); user.setFirstName("Ankit");
//		 user.setLastName("Kumar"); user.setUsername("ankit6220");
//		  user.setPassword("ankit6220@"); user.setEmail("ankitkumar6220@gmial.com");
//		  user.setProfile("default.png"); user.setPhone("8851421261");
//		  
//		  Role role1=new Role(); role1.setRoleId(44L); role1.setRoleName("ADMIN");
//		  
//		  Set<UserRole> userRoleSet =new HashSet<>(); UserRole userRole=new UserRole();
//		  userRole.setRole(role1); userRole.setUser(user);
//		  
//		  userRoleSet.add(userRole);
//		  
//		  User user1= this.userService.createUser(user, userRoleSet);
//		  System.out.println(user1.getUsername());
		 
//	}

}
