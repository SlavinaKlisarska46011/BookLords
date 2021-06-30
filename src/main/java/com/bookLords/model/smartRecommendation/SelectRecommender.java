package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.Rating;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SelectRecommender {
    @Autowired
    SlopeOneRecommender slopeOneRecommender;

    @Autowired
    ContentBasedRecommender contentBasedRecommender;

    @Autowired
    BookDBDAO bookDBDAO;

    public Recommender selectRecommender(User user) {
        Set<Bookshelf> userBookshelves = user.getBookshelves();
        Map<Integer, Rating> userRatings = user.getRatings();
        Set<Book> userBooks = new HashSet<>();
        if (!userBookshelves.isEmpty()) {
            for (Bookshelf bookshelf : userBookshelves) {
                userBooks.addAll(bookshelf.getBooks().keySet());
            }
        }

        if (!userRatings.isEmpty()) {
            for (int bookId : userRatings.keySet()){
                try {
                    userBooks.add(bookDBDAO.getBookByID(bookId));
                } catch (BookException e) {
                }
            }
        }

        return !userBooks.isEmpty() ? contentBasedRecommender : slopeOneRecommender;
    }
}
