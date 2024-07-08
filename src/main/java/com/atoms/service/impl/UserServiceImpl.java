package com.atoms.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atoms.dto.UserProfile;
import com.atoms.exceptions.ErrorSavingObjectInDatabase;
import com.atoms.exceptions.UserAlreadyExist;
import com.atoms.exceptions.UserIsInactive;
import com.atoms.exceptions.UserNotFoundException;
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

	public UserProfile createUser(User user)
			throws ValidationException, UserAlreadyExist, ErrorSavingObjectInDatabase, UserIsInactive {
		LOGGER.info("Inside UserServiceImpl createUser");
		
		UserProfile userProfile = null;
		User newUser = null;
		validateRegistration(user);
		User local = this.userRepository.findByUsername(user.getUsername());
		if (local != null) {
			LOGGER.info("Inside UserServiceImpl createUser");
			if (!local.isEnable()) {
				throw new UserIsInactive("User is Already Present and InActive :" + user.getUsername());
			}
			throw new UserAlreadyExist("User Name already present :" + user.getUsername());
		} else {
			LOGGER.info("Inside UserServiceImpl createUser -- create user");
			Role role = roleRespository.findByRoleName("Student");
			// assign all the roles to the user
			Set<UserRole> roles = new HashSet<UserRole>();
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			roles.add(userRole);

			user.setUserRoles(roles);
			newUser = this.userRepository.save(user);
			if (newUser == null) {
				LOGGER.info("Inside UserServiceImpl createUser -- Error Saving Object In Database For Username ");
				throw new ErrorSavingObjectInDatabase(
						"Error Saving Object In Database For Username :" + user.getUsername());
			}else {
				userProfile=setUsertoUserProfile(newUser);
			}
		}
		return userProfile;
	}

	private UserProfile setUsertoUserProfile(User user) {
		LOGGER.info("Inside UserServiceImpl setUsertoUserProfile -- create UserProfile");
		UserProfile userProfile=new UserProfile();
		userProfile.setUsername(user.getUsername());
		
		Set<UserRole>roles= user.getUserRoles();
		for(UserRole r:roles) {
			userProfile.setRole(r.getRole().getRoleName());
		}
		userProfile.setEmail(user.getEmail());
		userProfile.setFirstName(user.getFirstName());
		userProfile.setLastName(user.getLastName());
		userProfile.setPhone(user.getPhone());
		return userProfile;
	}

	public UserProfile getUser(String username) throws UserNotFoundException {
		LOGGER.info("Inside UserServiceImpl getUser -- ");
		UserProfile userProfile=null;
		User user = this.userRepository.findByUsername(username);
		
		if(user!=null) {
			userProfile=setUsertoUserProfile(user);
		}else {
			LOGGER.info("Inside UserServiceImpl getUser -- No Username found"+username);
			throw new UserNotFoundException("No Username found :"+username);
		}
		return userProfile;
	}

	private void validateRegistration(User user) throws ValidationException {
		LOGGER.info("Inside UserServiceImpl validateRegistration");
		
		if (user.getUsername() == null) {
			throw new ValidationException("Incoming Request Username Not found::Cannot proceed");
		}
		if (user.getEmail() == null) {
			throw new ValidationException("Incoming Request Object Email Not found::Cannot proceed");
		}
		if (user.getFirstName() == null) {
			throw new ValidationException("Incoming Request Object Name Not found::Cannot proceed");
		}
		if (user.getPassword() == null) {
			throw new ValidationException("Incoming Request Object Password Not found::Cannot proceed");
		}
		if (user.getPhone() == null) {
			throw new ValidationException("Incoming Request Object Phone Not found::Cannot proceed");
		}

	}

	public void deleteUser(Long userId) throws ValidationException {
		LOGGER.info("Inside UserServiceImpl deleteUser -- ");
		if(userId!=null) {
			this.userRepository.deleteById(userId);
			LOGGER.info("Inside UserServiceImpl deleteUser --DONE ");
		}else {
			LOGGER.info("Inside UserServiceImpl deleteUser --User ID Not found : "+userId);
			throw new ValidationException("User ID Not found :"+userId);
		}
		
	}

	public UserProfile updateUser(User user) throws ErrorSavingObjectInDatabase, UserNotFoundException, ValidationException {
		UserProfile userProfile=null;
		User updatedUser=null;
		LOGGER.info("Inside UserServiceImpl updateUser -- ");
		if(user.getId()!=null) {
			User existingUser = userRepository.findById(user.getId()).orElse(null);
			if(existingUser!=null) {
				LOGGER.info("Inside UserServiceImpl updateUser -- Updating");
				//existingUser.setEmail(user.getEmail());
				existingUser.setFirstName(user.getFirstName());
				existingUser.setLastName(user.getLastName());
				existingUser.setPassword(user.getPassword());
				existingUser.setPhone(user.getPhone());
				existingUser.setProfile(user.getProfile());
				existingUser.setUsername(user.getUsername());

				updatedUser=userRepository.save(existingUser);
				LOGGER.info("Inside UserServiceImpl updateUser -- Updated");
				if(updatedUser!=null) {
					userProfile=setUsertoUserProfile(updatedUser);
				}else {
					LOGGER.info("Inside UserServiceImpl updateUser -- Error Saving Object In Database For Username"+existingUser.getUsername());
					throw new ErrorSavingObjectInDatabase(
							"Error Saving Object In Database For Username :" + existingUser.getUsername());
				}
			}else {
				LOGGER.info("Inside UserServiceImpl updateUser -- User Not found "+user.getUsername());
				throw new UserNotFoundException("User Not found :"+user.getUsername());
			}
			
		}else {
			LOGGER.info("Inside UserServiceImpl updateUser -- User ID Not found : "+user.getUsername());
			throw new ValidationException("User ID Not found :"+user.getUsername());
		}
		
		LOGGER.info("Inside UserServiceImpl updateUser -- DONE");
		return userProfile;
	}

}
