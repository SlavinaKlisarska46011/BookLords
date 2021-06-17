package com.bookLords.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.BookException;

import java.util.List;

public interface Recommender {

   List<Book> recommend(User user) throws BookException;

}
