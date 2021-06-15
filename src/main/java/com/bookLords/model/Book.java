package com.bookLords.model;

import com.bookLords.model.exceptions.CommentException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.RatingException;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Book {
	private int bookId;
	private String title;
	private Set<Author> authors = new LinkedHashSet<Author>();
	private String description;
	private String posterURL;
	private String ISBN;
	private int pages;
	private String editionLanguage;
	private Set<String> genres = new LinkedHashSet<String>();
	private String readOnlineURL;
	private String buyOnlineURL;
	
	// country, year:
	private String setting;
	
	private double rating;
	private long numberOfRatings;

	// LinkedHashSet to be in the order in which they came:
	private Set<Comment> comments = new LinkedHashSet<Comment>();

	// ---------This constructor is used by BookApiDAO---------
	public Book(String title, Set<Author> authors, String publisher, String publishedDate, String description,
			String ISBN, int pages, String genres, String smallImage, String language, String readOnlineURL,
			String buyOnlineURL) throws InvalidDataException {

		this.title = isValidString(title);
		if (authors != null) {
			this.authors.addAll(authors);
		}
		this.setting = "published by: " + publisher + " on " + publishedDate;
		this.description = isValidString(description);
		this.ISBN = isValidString(ISBN);
		if (pages >= 0) {
			this.pages = pages;
		} else {
			throw new InvalidDataException("Invalid number of pages!");
		}
		if (genres != null) {
			this.genres.add(genres);
		}
		this.posterURL = isValidString(smallImage);
		this.editionLanguage = isValidString(language);
		this.readOnlineURL = isValidString(readOnlineURL);
		this.buyOnlineURL = isValidString(buyOnlineURL);
	}

	// ---------This constructor is used by BookDBDAO---------
	public Book(int bookID, String title, Set<Author> authors, String setting, String description, String ISBN,
			int pages, Set<String> genres, String smallImage, String language, String readOnlineURL,
			String buyOnlineURL, double rating, long numberOfRatings, Set<Comment> comments)
			throws RatingException, InvalidDataException, CommentException {
		setBookId(bookID);
		this.title = isValidString(title);
		
		if (authors != null) {
			this.authors.addAll(authors);
		}
		this.setting = isValidString(setting);
		this.description = isValidString(description);
		this.ISBN = isValidString(ISBN);
		if (pages >= 0) {
			this.pages = pages;
		} else {
			throw new InvalidDataException("Invalid number of pages!");
		}
		if (genres != null) {
			this.genres.addAll(genres);
		}
		this.posterURL = isValidString(smallImage);
		this.editionLanguage = isValidString(language);
		this.readOnlineURL = isValidString(readOnlineURL);
		this.buyOnlineURL = isValidString(buyOnlineURL);
		setRating(rating);
		setNumberOfRatings(numberOfRatings);
		if (comments != null) {
			this.comments = comments;
		} else {
			throw new CommentException("Sorry not excepting null for comments!");
		}
	}

	public Book() {
	}

	public String isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return string;
		} else {
			return "none";
		}
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) throws InvalidDataException {
		if (bookId >= 0) {
			this.bookId = bookId;
		} else {
			throw new InvalidDataException("Invalid book id!");
		}
	}

	public String getTitle() {
		return title;
	}

	public Set<Author> getAuthors() {
		return Collections.unmodifiableSet(authors);
	}

	public Set<String> getGenres() {
		return genres;
	}

	public String getDescription() {
		return description;
	}

	public String getPosterURL() {
		return posterURL;
	}

	public String getISBN() {
		return ISBN;
	}

	public int getPages() {
		return pages;
	}

	public String getEditionLanguage() {
		return editionLanguage;
	}

	public String getReadOnlineURL() {
		return readOnlineURL;
	}

	public String getBuyOnlineURL() {
		return buyOnlineURL;
	}

	public String getSetting() {
		return setting;
	}

	// Return a copy of the collection to avoid the client possibility to modify
	// it
	public Set<Comment> getComments() {
		Set<Comment> comments = new LinkedHashSet<Comment>(this.comments);
		return comments;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) throws RatingException {
		if (rating >= 0 && rating <= 5) {
			this.rating = rating;
		} else {
			throw new RatingException("Invalid rating, sorry!");
		}
	}

	public long getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(long numberOfRatings) throws InvalidDataException {
		if (numberOfRatings >= 0) {
			this.numberOfRatings = numberOfRatings;
		} else {
			throw new InvalidDataException("Invalid number of ratings!");
		}
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", authors=" + authors + ", description=" + description + ", posterURL="
				+ posterURL + ", ISBN=" + ISBN + ", pages=" + pages + ", editionLanguage=" + editionLanguage
				+ ", genres=" + genres + ", readOnlineURL=" + readOnlineURL + ", buyOnlineURL=" + buyOnlineURL
				+ ", setting=" + setting + "]";
	}

}
