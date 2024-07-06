package com.atoms.exceptions;

public class UserAlreadyExist extends Exception{

	public UserAlreadyExist(String message) {
		super(message);
	}

	public UserAlreadyExist(Throwable throwable) {
		super(throwable);
	}

	public UserAlreadyExist(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
