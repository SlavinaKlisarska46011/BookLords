package com.bookLords.model.interfaces;

import com.bookLords.model.Book;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.exceptions.InvalidDataException;

import java.util.Map;
import java.util.Set;

public interface IBookshelvesDAO {

	public Bookshelf addBookshelf(String bookshelfName, int userId) throws BookshelfException, InvalidDataException;
	
	public boolean addBookToBookshelf(int bookshelfId, int bookId) throws BookshelfException;
	
	public Set<Bookshelf> getUserBookshelves(int userId) throws BookshelfException;
	
	public Map<Book, String> getBooksFromBookShelf(int bookshelfId) throws BookException;
	
	public Set<Bookshelf> addDefaultBookshelves(int userId) throws BookshelfException;
}
