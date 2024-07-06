package com.atoms.exceptions;

public class ErrorSavingObjectInDatabase extends Exception{

	public ErrorSavingObjectInDatabase(String message) {
		super(message);
	}

	public ErrorSavingObjectInDatabase(Throwable throwable) {
		super(throwable);
	}

	public ErrorSavingObjectInDatabase(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
