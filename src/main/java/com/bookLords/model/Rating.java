package com.bookLords.model;

import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.RatingException;
import com.bookLords.model.interfaces.ICheck;

public class Rating implements ICheck {

	private int id;
	private int rating;
	private String dateAdded;
	private Book book;

	public Rating(String date, int rating) throws InvalidDataException, RatingException {
		this.book = book;
		if (rating >= 0 && rating <= 5) {
			this.rating = rating;
		} else {
			throw new RatingException("Invalid rating, sorry!");
		}
		this.dateAdded=isValidString(date);
	}

	public int getRating() {
		return rating;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public int getId() {
		return id;
	}

	// LinkedHashSet
	// private Set<String> comments;
}
