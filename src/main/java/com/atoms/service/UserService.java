package com.atoms.service;


import com.atoms.model.User;

public interface UserService {
	
	//creating user
	//one user can have multiple Roles
	public User createUser(User user) throws Exception;

	//get user by username
	public User getUser(String username);

	//DELETING USER
		public void deleteUser(Long userId);

		// UPDATE USER BY USER ID
		public User updateUser(User user);
}
