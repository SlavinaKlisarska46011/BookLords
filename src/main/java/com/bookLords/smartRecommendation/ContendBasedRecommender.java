package com.bookLords.smartRecommendation;

import com.bookLords.model.*;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ContendBasedRecommender {

    @Autowired
    BookDBDAO bookDBDAO;

    public void recommend(User user) throws BookException {
        Map<String, Integer> favouriteGenres = new HashMap<>();
        Map<Author, Integer> favouriteAuthors = new HashMap<>();

        populate(user, favouriteGenres, favouriteAuthors);
        Map<String, Integer> sortedFaveGenres = sortByValue(favouriteGenres);
        Map<Author, Integer> sortedFaveAuthors = sortByValueAuthors(favouriteAuthors);

        int sizeGenres = sortedFaveGenres.size();
        int sizeAuthors = sortedFaveAuthors.size();
        int size = sizeGenres > 0 ? sizeGenres : 5;
        int size2 = sizeAuthors > 0 ? sizeAuthors : 5;

        List<Book> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size2; j++) {

            }
        }
//        bookDBDAO.getBooksByGenreAndAuthor() limit
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

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortedMap)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) ->
//                        order ?
                                o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue())
//                : o2.getValue().compareTo(o1.getValue()) == 0
//                ? o2.getKey().compareTo(o1.getKey())
//                : o2.getValue().compareTo(o1.getValue())
        );
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }

    private Map<Author, Integer> sortByValueAuthors(Map<Author, Integer> unsortedMap) {
        List<Map.Entry<Author, Integer>> list = new LinkedList<>(unsortedMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) ->
//                        order ?
                        o1.getValue().compareTo(o2.getValue()) == 0
                                ? o1.getKey().getName().compareTo(o2.getKey().getName())
                                : o1.getValue().compareTo(o2.getValue())
//                : o2.getValue().compareTo(o1.getValue()) == 0
//                ? o2.getKey().compareTo(o1.getKey())
//                : o2.getValue().compareTo(o1.getValue())
        );
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
}
