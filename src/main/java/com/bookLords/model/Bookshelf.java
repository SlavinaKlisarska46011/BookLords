package com.bookLords.model;

import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.interfaces.ICheck;

import java.util.HashMap;
import java.util.Map;

public class Bookshelf implements ICheck {

	private int id;
	private String name;
	// book -> date added
	private Map<Book, String> books = new HashMap<>();

	public Bookshelf(int id, String bookshelfName, Map<Book, String> books)
			throws InvalidDataException, BookshelfException {
		this.id = isValidId(id);
		this.name = isValidString(bookshelfName);
		this.books = books;
	}

	public Bookshelf(int id, String bookshelfName) throws InvalidDataException {
		this.id = isValidId(id);
		// this.name = isValidString(bookshelfName);
		this.name = bookshelfName;
	}

	public Bookshelf() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<Book, String> getBooks() {
		// Map<Book, LocalDate> books = new HashMap<Book,
		// LocalDate>(this.books);
		return books;
	}

	@Override
	public String toString() {
		return "Bookshelf [id=" + id + ", bookshelfName=" + name + ", books=" + books + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bookshelf other = (Bookshelf) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}

