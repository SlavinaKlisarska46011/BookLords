package com.bookLords.model.daos;

import com.bookLords.model.Book;
import com.bookLords.model.DBConnection;
import com.bookLords.model.Rating;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.RatingException;
import com.bookLords.model.interfaces.IRatingsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.Action;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class RatingsDAO implements IRatingsDAO {

    private static Connection connection = DBConnection.getInstance().getConnection();
    @Autowired
    private BookDBDAO bookDBDAO;

    public void updateRatings(User user, Book book, int rating, LocalDateTime date)
            throws RatingException, InvalidDataException {
        try {
            addRating(user, rating, book.getBookId(), date);
            updateBookRatings(book, rating);
        } catch (RatingException e) {
            e.printStackTrace();
            throw new RatingException("Sorry, couldn`t update ratings!");
        }
    }

    private void addRating(User user, int rating, int bookId, LocalDateTime date)
            throws RatingException, InvalidDataException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO ratings VALUES(null,?, ?, ?, ?);");
            preparedStatement.setInt(1, rating);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setString(3, date.toString());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();

            user.getRatings().put(bookId, new Rating(date.toString(), rating));

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RatingException("Invalid rating!");
        }
    }

    public Map<Integer, Rating> getUserRatings(int userId) throws RatingException, InvalidDataException {
        Map<Integer, Rating> userRatings = new HashMap<>();
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM ratings WHERE user_id=? ;");
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
//				int ratingId = resultSet.getInt(1);
                int rating = resultSet.getInt(2);
                int bookId = resultSet.getInt(3);
                String date = resultSet.getString(4);
                userRatings.put(bookId, new Rating(date, rating));
            }
            return userRatings;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RatingException("Sorry, couldn`t load ratings!");
        }
    }

    private void updateBookRatings(Book book, int rating) throws RatingException {
        PreparedStatement preparedStatement;
        try {
            long numberOfRatings = book.getNumberOfRatings() + 1;
            double newRating = (book.getRating() + rating) / numberOfRatings;
            newRating = Math.round(newRating * 100) / 100.0d;
            preparedStatement = connection
                    .prepareStatement("UPDATE books SET rating=?, number_of_ratings=? WHERE book_id=?;");

            preparedStatement.setDouble(1, newRating);
            preparedStatement.setLong(2, numberOfRatings);
            preparedStatement.setInt(3, book.getBookId());
            preparedStatement.executeUpdate();
            book.setRating(newRating);
            book.setNumberOfRatings(numberOfRatings);
        } catch (SQLException | InvalidDataException e) {
            e.printStackTrace();
            throw new RatingException("Sorry, couldn`t update book rating!");
        }
    }
}
