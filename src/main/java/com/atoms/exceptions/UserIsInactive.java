package com.atoms.exceptions;

public class UserIsInactive extends Exception {

	public UserIsInactive(String message) {
		super(message);
	}

	public UserIsInactive(Throwable throwable) {
		super(throwable);
	}

	public UserIsInactive(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
