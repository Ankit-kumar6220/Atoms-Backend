package com.atoms.exceptions;

public class UserNotFoundException extends Exception{

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable throwable) {
		super(throwable);
	}

	public UserNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
