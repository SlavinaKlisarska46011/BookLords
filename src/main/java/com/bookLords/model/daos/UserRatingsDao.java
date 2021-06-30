package com.bookLords.model.daos;

import com.bookLords.model.Book;
import com.bookLords.model.DBConnection;
import com.bookLords.model.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserRatingsDao {

    @Autowired
    BookDBDAO bookDBDAO;

    @Autowired
    UserProfileDAO userDBDao;

    private Connection connection = DBConnection.getInstance().getConnection();

    public synchronized ConcurrentHashMap<Integer, HashMap<Book, Double>> getAllUsersRatings() {
        ConcurrentHashMap<Integer, HashMap<Book, Double>> result = new ConcurrentHashMap<>();
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT users.user_id, books.book_id, ratings.rating " +
                            "FROM ratings " +
                            "JOIN books ON books.book_id = ratings.book_id " +
                            "JOIN users ON users.user_id = ratings.user_id;");

            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                int userId = resultSet.getInt("user_id");
                int rating = resultSet.getInt("rating");

                connection.commit();

                if (result.containsKey(userId)) {
                    result.get(userId).put(bookDBDAO.getBookByID(bookId), (double) rating);
                } else {
                    HashMap<Book, Double> bookRating = new HashMap<>();
                    bookRating.put(bookDBDAO.getBookByID(bookId), (double) rating);
                    result.put(userId, bookRating);
                }

                for (int user : getAllUserIds()) {
                    result.putIfAbsent(user, new HashMap<>());
                }
            }
            return result;
        } catch (SQLException | BookException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();

            }
        }
        return null;
    }

    public Set<Integer> getAllUserIds() {
        Set<Integer> result = new HashSet<>();
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement
                    .executeQuery("SELECT users.user_id FROM users;");

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");

                connection.commit();
                result.add(userId);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();

            }
        }
        return null;
    }
}
