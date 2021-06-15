package com.bookLords.model;

import com.bookLords.model.exceptions.AuthorException;
import com.bookLords.model.exceptions.InvalidDataException;

import java.util.Date;
import java.util.Set;

public class Author {

	private int id;

	private String name;
	private String imageURL;

	// where + date
	private String born;
	private String died;
	private String biography;
	private Set<Genre> genre;


	// =========This constructor is used by AuthorApiDAO==========
	public Author(String name, String email, String password, String born, String biography, Date memberSince)
			throws InvalidDataException {
	}

	// =========This constructor is used by BookApiDAO==========
	public Author(String name) throws InvalidDataException {
		if (isValidString(name)) {
			this.name = name;
		} else {
			this.name = "none";
		}
	}

	// =========This constructor is used by AuthorApiDAO==========
	public Author(int id, String name, String imgURL, String born, String died, String biography)
			throws InvalidDataException, AuthorException {
		this(name);
		if (id > 0) {
			this.id = id;
		} else {
			throw new AuthorException("Invalid author id!");
		}
		if (isValidString(imgURL)) {
			this.imageURL = imgURL;
		} else {
			this.imageURL = "none";
		}
		if (isValidString(born)) {
			this.born = born;
		} else {
			this.born = "none";
		}
		if (isValidString(died)) {
			this.died = died;
		} else {
			this.died = "none";
		}
		if (isValidString(biography)) {
			this.biography = biography;
		} else {
			this.biography = "none";
		}
	}

	public boolean isValidString(String string) throws InvalidDataException {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getBorn() {
		return born;
	}

	public String getDied() {
		return died;
	}

	public Set<Genre> getGenre() {
		return genre;
	}

	public String getBiography() {
		return biography;
	}

	public String getImageURL() {
		return imageURL;
	}

	@Override
	public String toString() {
		return "Author [id=" + id + ", name=" + name + ", imageURL=" + imageURL + ", born=" + born + ", died=" + died
				+ ", biography=" + biography + ", genre=" + genre + "]";
	}

}
