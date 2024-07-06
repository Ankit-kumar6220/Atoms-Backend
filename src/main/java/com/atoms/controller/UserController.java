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

import com.atoms.exceptions.ErrorSavingObjectInDatabase;
import com.atoms.exceptions.UserAlreadyExist;
import com.atoms.exceptions.UserIsBlocked;
import com.atoms.exceptions.UserIsInactive;
import com.atoms.model.User;
import com.atoms.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
	@Autowired
	private UserService userService;
	
	//creating user
	@PostMapping("/")
	public ResponseEntity<?> createUser(@RequestBody User user){	
		//setting that the new user is Normal user only

		if(user!=null) {
			 try {
				this.userService.createUser(user);
			} 
			 catch(UserAlreadyExist e) {
				LOGGER.error("UserAuthController Error UserAlreadyExist-"+user.getEmail());
				return ResponseEntity.status(HttpStatus.FOUND)
									.body(e.getMessage());
			}
			  catch(UserIsBlocked e) {
			 LOGGER.error("UserAuthController Error UserIsBlocked-"+user.getEmail());
			 return ResponseEntity.status(HttpStatus.FOUND) .body(e.getMessage()); 
			 }
			 
			 catch(UserIsInactive e) {
				LOGGER.error("UserAuthController Error UserIsInactive-"+user.getEmail());
				return ResponseEntity.status(HttpStatus.FOUND)
									.body(e.getMessage());
			} catch (ErrorSavingObjectInDatabase e) {
				LOGGER.error("ErrorSavingObjectInDatabase-"+user.getEmail());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body(e.getMessage());
				
			} catch (Exception e) {
				LOGGER.error("Some Exception Occurs-"+user.getEmail());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
									.body(e.getMessage());
			}
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(user);
		
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String username) {
		
		if(username!= null) {
		       User user=this.userService.getUser(username);
			if(user !=null) {
				 return ResponseEntity.status(HttpStatus.OK)
						.body(user);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("User Not Found");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body("User Name is Null !!");
		
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userID") Long userId) {
		 this .userService.deleteUser(userId);
		
	}
	
	@PutMapping("/updateuser")
	public void updateUser(@RequestBody User user) {
		this.userService.updateUser(user);
	}
	
}
