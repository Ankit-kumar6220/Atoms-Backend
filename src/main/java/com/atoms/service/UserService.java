package com.atoms.service;

import com.atoms.dto.UserProfile;
import com.atoms.model.User;

public interface UserService {

	// creating user
	// one user can have multiple Roles
	public UserProfile createUser(User user) throws Exception;

	// get user by username
	public UserProfile getUser(String username) throws Exception;

	// DELETING USER
	public void deleteUser(Long userId) throws Exception;;

	// UPDATE USER BY USER ID
	public UserProfile updateUser(User user) throws Exception;;
}
