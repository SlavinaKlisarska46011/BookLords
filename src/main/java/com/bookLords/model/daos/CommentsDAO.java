package com.bookLords.model.daos;

import com.bookLords.model.Book;
import com.bookLords.model.Comment;
import com.bookLords.model.DBConnection;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.*;
import com.bookLords.model.interfaces.ICommentsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class CommentsDAO implements ICommentsDAO{
	private static final String SELECT_FROM_COMMENTS = "SELECT * FROM comments WHERE book_id= ? ORDER BY comment_id desc";
	private static final String INSERT_INTO_COMMENTS = "INSERT INTO comments VALUES(null,?,?,?,?);";
	private Connection connection = DBConnection.getInstance().getConnection();
	@Autowired
	UserProfileDAO userDao;
	
	@Autowired
	private BookDBDAO bookDBDAO;

	public synchronized int addComment(Comment comment) {
		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_INTO_COMMENTS, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, comment.getText());
			ps.setInt(2, comment.getUser().getId());
			System.out.println(comment.getUser().getId());
			ps.setInt(3, comment.getBookId());
			System.out.println(comment.getBookId());
			ps.setString(4, comment.getDate());
			System.out.println(comment.getDate());
			ps.executeUpdate();
			ResultSet resultSet = ps.getGeneratedKeys();
			if (resultSet.next()) {
				System.out.println(resultSet.getInt(1));
			}
			return resultSet.getInt(1);
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Set<Comment> getBookComments(int bookId) throws CommentException {
		Set<Comment> comments = new LinkedHashSet<Comment>();
		try {
			if (bookId > 0) {
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_COMMENTS);
				preparedStatement.setInt(1, bookId);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					int commentId = resultSet.getInt(1);
					String commentText = resultSet.getString(2);
					int userId = resultSet.getInt(3);
					String date = resultSet.getString(5);
					User user = userDao.getUserById(userId);
					connection.commit();
					comments.add(new Comment(commentId, commentText, user, date));
				}
				return comments;
			}
		} catch (SQLException | InvalidDataException | UserException | BookshelfException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new CommentException("Couldn`t load comment!");
			}
			throw new CommentException("Couldn`t load comment!");
		}
		return null;

	}
	
	public Map<Book, String> getCommentsById(int userId) throws BookException {
		Map<Book, String> booksWithComments = new HashMap<Book, String>();
		try {
			if (userId > 0) {
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM comments WHERE user_id = ?");
				preparedStatement.setInt(1, userId);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					String textComment = resultSet.getString("text");
					int bookId = resultSet.getInt("book_id");
					
					Book book = bookDBDAO.getBookByID(bookId);
					connection.commit();
					booksWithComments.put(book, textComment);
				}
				return booksWithComments;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new BookException("Couldn`t load this book.");
			}
			throw new BookException("Couldn`t load this book.");
		}
		return booksWithComments;
	}

}
