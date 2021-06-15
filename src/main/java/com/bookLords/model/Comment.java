package com.bookLords.model;

import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.ICheck;

public class Comment implements ICheck {
	private int commentId;
	private User user;
	private String text;
	private int bookId;
	private String date;

	public Comment() {
	}

	public Comment(int commentId, String text, User user, String date)
			throws InvalidDataException, UserException, BookshelfException {
		this.commentId = commentId;
		this.text = text;
		setUser(user);
		setDate(date);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) throws UserException {
		if (user != null) {
			this.user = user;
		} else {
			throw new UserException("No such user found!");
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) throws InvalidDataException {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setDate(String date) throws BookshelfException, InvalidDataException {
		this.date=date;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) throws BookshelfException, InvalidDataException {
		this.bookId=bookId;
	}

}
