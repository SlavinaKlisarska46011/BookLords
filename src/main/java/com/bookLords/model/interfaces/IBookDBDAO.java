package com.bookLords.model.interfaces;

import com.bookLords.model.Author;
import com.bookLords.model.Book;
import com.bookLords.model.exceptions.AuthorException;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;

import java.util.Set;

public interface IBookDBDAO {
	void addBooks(Set<Book> books) throws BookException;

	Set<Book> getBooksByTitle(String bookTitle) throws BookException;

	Set<Book> getBooksByAuthor(String author) throws BookException, InvalidDataException;

	Set<Book> getBooksByGenre(String genre) throws BookException;

	Book getBookByID(int bookId) throws BookException;

	Author getAuthorById(int authorId) throws AuthorException;
}
