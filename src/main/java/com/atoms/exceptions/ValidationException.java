package com.atoms.exceptions;

public class ValidationException extends Exception {

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable throwable) {
		super(throwable);
	}

	public ValidationException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
