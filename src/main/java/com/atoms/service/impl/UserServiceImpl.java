package com.atoms.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.atoms.exceptions.ErrorSavingObjectInDatabase;
import com.atoms.exceptions.UserAlreadyExist;
import com.atoms.exceptions.UserIsInactive;
import com.atoms.exceptions.ValidationException;
import com.atoms.model.Role;
import com.atoms.model.User;
import com.atoms.model.UserRole;
import com.atoms.repository.RoleRespository;
import com.atoms.repository.UserRepository;
import com.atoms.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRespository roleRespository;

	@Override
	public User createUser(User user) throws ValidationException, UserAlreadyExist, ErrorSavingObjectInDatabase, UserIsInactive  {
		User newUser = null;
		validateRegistration(user);
		User local = this.userRepository.findByUsername(user.getUsername());
		if (local != null) {
			if(!local.isEnable()) {
				throw new UserIsInactive("User is Already Present and InActive :" + user.getUsername());
			}
			throw new UserAlreadyExist("User Name already present :" + user.getUsername());
		} else {
			// create user
			// fetch normal role from database
			Role role = roleRespository.findByRoleName("NORMAL");
			// assign all the roles to the user
			Set<UserRole> roles = new HashSet<>();
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			roles.add(userRole);

			user.setUserRoles(roles);
			newUser = this.userRepository.save(user);
			if (newUser == null) {
				throw new ErrorSavingObjectInDatabase(
						"Error Saving Object In Database For Username :" + user.getUsername());
			}
		}
		return newUser;
	}

	//getting user by username
	@Override
	public User getUser(String username) {
		System.out.println();
		User user2=this.userRepository.findByUsername(username);
		System.out.println("User Role"+user2.getUserRoles());
		return user2;
	}

	
	private void validateRegistration(User user) throws ValidationException {
		if (user.getUsername() == null) {
			throw new ValidationException("Incoming Request Username Not found::Cannot proceed");
		}
		if (user.getEmail()==null) {
			throw new ValidationException("Incoming Request Object Email Not found::Cannot proceed");
		}
		if (user.getFirstName()==null) {
			throw new ValidationException("Incoming Request Object Name Not found::Cannot proceed");
		}
		if (user.getPassword()==null) {
			throw new ValidationException("Incoming Request Object Password Not found::Cannot proceed");
		}
		if (user.getPhone()==null) {
			throw new ValidationException("Incoming Request Object Phone Not found::Cannot proceed");
		}
		
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		this.userRepository.deleteById(userId);
	}

	//UPDATE USER BY USERID
		@Override
		public User updateUser(User user) {
			User existingUser=userRepository.findById(user.getId()).orElse(null);
			existingUser.setEmail(user.getEmail());
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setPassword(user.getPassword());
			existingUser.setPhone(user.getPhone());
			existingUser.setProfile(user.getProfile());
			existingUser.setUsername(user.getUsername());

			return userRepository.save(existingUser);
		}

	
}
