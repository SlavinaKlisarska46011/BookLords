package com.bookLords.smartRecommendation;

import com.bookLords.model.daos.BookDBDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class SlopeOneRecommender {

    @Autowired
    BookDBDAO bookDBDAO;

//    public void recommend(User user) throws BookException {
//        Map<Integer, Genre> favouriteGenres = new TreeMap<>();
//        Map<Integer, Author> favouriteAuthors = new TreeMap<>();
//
//        for(Map.Entry<Integer, Rating> rating : user.getRatings().entrySet()){
//            Book bookByID = bookDBDAO.getBookByID(rating.getKey());
//            favouriteGenres.put(1, bookByID.getGenres());
//
//            favouriteAuthors.put(1, bookByID.getAuthors());
//        }
//    }
}
