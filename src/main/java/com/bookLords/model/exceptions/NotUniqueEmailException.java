package com.bookLords.model.exceptions;

public class NotUniqueEmailException extends Exception{

	private static final long serialVersionUID = -4859790308203056798L;

	public NotUniqueEmailException() {
		super();
	}

	public NotUniqueEmailException(String message, Exception trace) {
		super(message, trace);
	}

	public NotUniqueEmailException(String message) {
		super(message);
	}

	public NotUniqueEmailException(Exception trace) {
		super(trace);
	}


}
