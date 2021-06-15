package com.bookLords.model.exceptions;

public class CommentException extends Exception {

	private static final long serialVersionUID = 643965438159637959L;

	public CommentException() {
		super();
	}

	public CommentException(String message, Exception trace) {
		super(message, trace);
	}

	public CommentException(String message) {
		super(message);
	}

	public CommentException(Exception trace) {
		super(trace);
	}

	
}
