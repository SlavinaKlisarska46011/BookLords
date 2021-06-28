package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.User;
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

    public Recommender selectRecommender(User user) {
        Set<Bookshelf> userBookshelves = user.getBookshelves();
        Set<Book> userBooks = new HashSet<>();
        if (!userBookshelves.isEmpty()) {
            for (Bookshelf bookshelf : userBookshelves) {
                userBooks.addAll(bookshelf.getBooks().keySet());
            }
        }
        return !userBooks.isEmpty() ? contentBasedRecommender : slopeOneRecommender;
    }
}
