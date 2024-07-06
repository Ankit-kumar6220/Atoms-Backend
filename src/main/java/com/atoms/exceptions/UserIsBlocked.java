package com.atoms.exceptions;

public class UserIsBlocked extends Exception{

	public UserIsBlocked(String message) {
		super(message);
	}

	public UserIsBlocked(Throwable throwable) {
		super(throwable);
	}

	public UserIsBlocked(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
