package com.bookLords.model.daos;

import com.bookLords.model.Book;
import com.bookLords.model.DBConnection;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserRatingsDao {

    @Autowired
    BookDBDAO bookDBDAO;

    @Autowired
    UserProfileDAO userDBDao;

    private Connection connection = DBConnection.getInstance().getConnection();

    public Map<User, HashMap<Book, Double>> getAllUsersRatings() {
        Map<User, HashMap<Book, Double>> result = new HashMap<>();
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
                HashMap<Book, Double> bookRating = new HashMap<>();
                bookRating.put(bookDBDAO.getBookByID(bookId), (double) rating);
                result.put(userDBDao.getUserById(userId), bookRating);
            }
            return result;
        } catch (SQLException | InvalidDataException | BookException e) {
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
