package com.bookLords.model.smartRecommendation;

import com.bookLords.model.*;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.daos.UserRatingsDao;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ContentBasedRecommender extends Recommender {

    @Autowired
    private BookDBDAO bookDBDAO;
    @Autowired
    private UserRatingsDao userRatingsDao;
    @Autowired
    private UserProfileDAO userProfileDao;

    private static ConcurrentHashMap<Integer, HashMap<Book, Double>> inputData;

    @PostConstruct
    public void init() throws BookException, SQLException, InvalidDataException {
        inputData = userRatingsDao.getAllUsersRatings();

        for (Map.Entry<Integer, HashMap<Book, Double>> entry : inputData.entrySet()) {
            Map<String, Integer> allUserGenres = new HashMap<>();
            Map<Author, Integer> allUserAuthors = new HashMap<>();
            allUserAuthors.remove("none");
            allUserGenres.remove("none");

            Integer userId = entry.getKey();
            populate(userProfileDao.getUserById(userId), allUserGenres, allUserAuthors);
            String sortedFaveGenres = sortByValue(allUserGenres);
            String sortedFaveAuthors = sortByValueAuthors(allUserAuthors);

            addToCash(userId, bookDBDAO.getBooksByGenreAndAuthor(sortedFaveGenres, sortedFaveAuthors));

        }
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
                .filter(books -> (!books.getKey().contains("\"") || !books.getKey().contains("\'")))
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
