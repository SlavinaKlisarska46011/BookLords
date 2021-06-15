package com.bookLords.model.exceptions;

public class BookshelfException extends Exception {

	private static final long serialVersionUID = -7138846957945000165L;

	public BookshelfException() {
	}

	public BookshelfException(String message) {
		super(message);
	}

	public BookshelfException(Exception trace) {
		super(trace);
	}

	public BookshelfException(String message, Exception trace) {
		super(message, trace);
	}

}
