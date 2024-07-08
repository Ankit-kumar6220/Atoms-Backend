package com.atoms.controller;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atoms.dto.UserProfile;
import com.atoms.exceptions.ErrorSavingObjectInDatabase;
import com.atoms.exceptions.UserAlreadyExist;
import com.atoms.exceptions.UserIsBlocked;
import com.atoms.exceptions.UserIsInactive;
import com.atoms.exceptions.UserNotFoundException;
import com.atoms.exceptions.ValidationException;
import com.atoms.model.User;
import com.atoms.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	final static Logger LOGGER = Logger.getLogger(UserController.class.getName());

	@Autowired
	private UserService userService;

	// creating user
	@PostMapping("/")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		UserProfile newUser=null;
		if (user != null) {
			try {
				LOGGER.info("Inside UserController createUser.");
				newUser=this.userService.createUser(user);
				
			} catch (ValidationException e) {
				LOGGER.error("UserAuthController Error Validation Exception for-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			} catch (UserAlreadyExist e) {
				LOGGER.error("UserAuthController Error UserAlreadyExist-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
			} catch (UserIsBlocked e) {
				LOGGER.error("UserAuthController Error UserIsBlocked-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
			} catch (UserIsInactive e) {
				LOGGER.error("UserAuthController Error UserIsInactive-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.FOUND).body(e.getMessage());
			} catch (ErrorSavingObjectInDatabase e) {
				LOGGER.error("ErrorSavingObjectInDatabase-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

			} catch (Exception e) {
				LOGGER.error("Some Exception Occurs-");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
		LOGGER.info("Inside UserController createUser SUCCESS.");
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);

	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		UserProfile userProfile = null;
		LOGGER.info("Inside UserController getUser");
		if (username != null) {
			try {
				
				userProfile = this.userService.getUser(username);
				
				LOGGER.info("Inside UserController getUser SUCCESS");
				return ResponseEntity.status(HttpStatus.OK).body(userProfile);
			} catch (UserNotFoundException e) {
				LOGGER.error("User Not Found -" + username);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Some Exception Occurs-");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}

		} else {
			LOGGER.info("Inside UserController getUser SUCCESS.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is Null !!");
		}
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userID") Long userId) {

		try {

			this.userService.deleteUser(userId);
			LOGGER.info("Inside UserController deleteUser SUCCESS");
			return ResponseEntity.status(HttpStatus.OK).body("User is Deleted.");
		} catch (ValidationException e) {
			LOGGER.error("UserAuthController Error Validation Exception for-" + userId);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Some Exception Occurs- for-" + userId);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}

	}

	@PutMapping("/updateuser")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		UserProfile userProfile = null;

		if (user != null) {
			LOGGER.info("Inside UserController updateUser");
			try {
				userProfile = this.userService.updateUser(user);
				
				LOGGER.info("Inside UserController updateUser SUCCESS");
				return ResponseEntity.status(HttpStatus.OK).body(userProfile);

			} catch (ValidationException e) {
				LOGGER.error("UserController Error Validation Exception for-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			} catch (UserNotFoundException e) {
				LOGGER.error("UserController Error User Not Found Exception for-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			} catch (ErrorSavingObjectInDatabase e) {
				LOGGER.error("UserController Error for Saving Object In Database-" + user.getEmail());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

			}catch (Exception e) {
				LOGGER.error("Some Exception Occurs-");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}

		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is Null !!");
		}

	}

}
