package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Recommender {

    private final ConcurrentHashMap<Integer, List<Book>> outputDataCash = new ConcurrentHashMap<>();

    public abstract void init() throws BookException, SQLException, InvalidDataException;

  public List<Book> recommend(User user) throws BookException, SQLException, InvalidDataException {
     if (outputDataCash.containsKey(user.getId())){
        return outputDataCash.get(user.getId());
     }
     init();
     return outputDataCash.get(user.getId());
   }

    protected void addToCash(Integer key, List<Book> value) {
        outputDataCash.put(key, value);
    }
}
