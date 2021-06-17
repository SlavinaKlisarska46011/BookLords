package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Author;
import com.bookLords.model.Book;
import com.bookLords.model.Rating;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ContentBasedRecommender {

    @Autowired
    BookDBDAO bookDBDAO;

    public void recommend(User user) throws BookException {
        Map<String, Integer> allUserGenres = new HashMap<>();
        Map<Author, Integer> allUserAuthors = new HashMap<>();

        populate(user, allUserGenres, allUserAuthors);
        String sortedFaveGenres = sortByValue(allUserGenres);
        String sortedFaveAuthors = sortByValueAuthors(allUserAuthors);

        user.addRecommendationsByContent(bookDBDAO.getBooksByGenreAndAuthor(sortedFaveGenres, sortedFaveAuthors));
    }


    private void populate(User user, Map<String, Integer> favouriteGenres, Map<Author, Integer> favouriteAuthors) throws BookException {
        for (Map.Entry<Integer, Rating> bookIdRating : user.getRatings().entrySet()) {
            Book bookByID = bookDBDAO.getBookByID(bookIdRating.getKey());
            for (String genre : bookByID.getGenres()) {
                if (favouriteGenres.containsKey(genre)) {
                    favouriteGenres.put(genre, favouriteGenres.get(genre) + 1);
                } else {
                    favouriteGenres.put(genre, 1);
                }
            }
            for (Author author : bookByID.getAuthors()) {
                if (favouriteAuthors.containsKey(author)) {
                    favouriteAuthors.put(author, favouriteAuthors.get(author) + 1);
                } else {
                    favouriteAuthors.put(author, 1);
                }
            }
        }
    }

    private static String sortByValue(Map<String, Integer> unsortedMap) {
        // Sorting the list based on values
        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining("','"));

    }

    private String sortByValueAuthors(Map<Author, Integer> unsortedMap) {
        return unsortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .map(authorIntegerEntry -> authorIntegerEntry.getKey().getName())
                .collect(Collectors.joining("','"));

    }
}
