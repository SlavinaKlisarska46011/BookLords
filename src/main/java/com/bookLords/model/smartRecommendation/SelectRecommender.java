package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.User;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class SelectRecommender {

    public Recommender selectRecommender(User user) {
        Set<Bookshelf> userBookshelves = user.getBookshelves();
        Set<Book> userBooks = new HashSet<>();
        if (!userBookshelves.isEmpty()) {
            for (Bookshelf bookshelf : userBookshelves) {
                Map<Book, String> books = bookshelf.getBooks();
                if (!books.isEmpty()) {
                    for (Book book : books.keySet()) {
                        userBooks.add(book);
                    }
                }
            }
            if (!userBooks.isEmpty()) {
                return new ContentBasedRecommender();
            }
        }
        return new SlopeOneRecommender();
    }
}
