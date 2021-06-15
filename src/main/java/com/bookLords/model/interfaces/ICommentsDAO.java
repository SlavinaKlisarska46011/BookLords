package com.bookLords.model.interfaces;

import com.bookLords.model.Book;
import com.bookLords.model.Comment;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.CommentException;

import java.util.Map;
import java.util.Set;

public interface ICommentsDAO {

	Set<Comment> getBookComments(int bookId) throws CommentException;

	int addComment(Comment comment);
	
	public Map<Book, String> getCommentsById(int userId) throws BookException;
}
