package com.bookLords.model.daos;

import com.bookLords.model.Book;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.DBConnection;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.interfaces.IBookshelvesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class BookshelvesDAO implements IBookshelvesDAO {
	private static final String INSERT_INTO_BOOKSHELF_NAMES_VALUES_NULL_TO_READ = "INSERT INTO bookshelf_names VALUES(null, 'To-Read');";
	private static final String SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME_LIKE_TO_READ = "SELECT* FROM bookshelf_names where bookshelf_name LIKE 'To-Read'";
	private static final String INSERT_INTO_BOOKSHELF_NAMES_VALUES_NULL_CURRENTLY_READING = "INSERT INTO bookshelf_names VALUES(null, 'Currently-Reading');";
	private static final String SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME_LIKE_CURRENTLY_READING = "SELECT* FROM bookshelf_names where bookshelf_name LIKE 'Currently-Reading'";
	private static final String INSERT_INTO_BOOKSHELF_NAMES_VALUES_NULL_READ = "INSERT INTO bookshelf_names VALUES(null, 'Read');";
	private static final String SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME_LIKE_READ = "SELECT* FROM bookshelf_names where bookshelf_name LIKE 'Read'";
	private static final String INSERT_INTO_BOOKSHELVES_HAS_BOOKS = "INSERT INTO bookshelves_has_books(bookshelves_bookshelf_id,books_book_id,date_book_was_added) VALUES(?, ?, ?);";
	private static final String INSERT_INTO_BOOKSHELVES = "INSERT INTO bookshelves VALUES(null, ?, ?);";
	private static final String INSERT_INTO_BOOKSHELF_NAMES = "INSERT INTO bookshelf_names VALUES(null,?);";
	private static final String SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME = "SELECT* FROM bookshelf_names where bookshelf_name = ?";
	private static final String SELECT_FROM_BOOKSHELF_NAMES = SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME;
	private Connection connection = DBConnection.getInstance().getConnection();

	@Autowired
	private BookDBDAO bookDBDAO;

	public synchronized Bookshelf addBookshelf(String bookshelfName, int userId)
			throws BookshelfException, InvalidDataException {
		try {
			connection.setAutoCommit(false);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FROM_BOOKSHELF_NAMES);
			preparedStatement.setString(1, bookshelfName);

			ResultSet resultSet = preparedStatement.executeQuery();

			int bookshelfNameId = 0;

			if (!resultSet.next()) {
				preparedStatement = connection.prepareStatement(INSERT_INTO_BOOKSHELF_NAMES,
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, bookshelfName);
				preparedStatement.executeUpdate();
				resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				bookshelfNameId = resultSet.getInt(1);

			} else {
				bookshelfNameId = resultSet.getInt(1);
			}
			preparedStatement = connection.prepareStatement(INSERT_INTO_BOOKSHELVES, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, bookshelfNameId);
			preparedStatement.setInt(2, userId);
			preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			int bookshelfId = resultSet.getInt(1);

			connection.commit();
			return new Bookshelf(bookshelfId, bookshelfName, getBooksFromBookShelf(bookshelfId));
		} catch (SQLException | BookException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new BookshelfException("Invalid bookshelf!");
			}
			throw new BookshelfException("Invalid bookshelf!");
		}
	}

	public synchronized boolean addBookToBookshelf(int bookshelfId, int bookId) throws BookshelfException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO_BOOKSHELVES_HAS_BOOKS);
			System.out.println(bookshelfId + " " + bookId);
			preparedStatement.setInt(1, bookshelfId);

			preparedStatement.setInt(2, bookId);
			preparedStatement.setDate(3, Date.valueOf(LocalDate.now()));
			preparedStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BookshelfException("Sorry couldn`t add this book in the bookshelf");
		}

	}

	public synchronized Set<Bookshelf> getUserBookshelves(int userId) throws BookshelfException {
		Set<Bookshelf> bookshelves = new LinkedHashSet<Bookshelf>();
		if (userId > 0) {
			try {

				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM bookshelves WHERE user_id = ?");
				preparedStatement.setInt(1, userId);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {

					int bookshelfId = resultSet.getInt(1);
					int bookshefNameId = resultSet.getInt(2);
					System.out.println("Eto sega ima" + bookshefNameId + bookshelfId);
					String bookshelfName = getBookshelfNameById(bookshefNameId);
					System.out.println(bookshelfName);
					bookshelves.add(new Bookshelf(bookshelfId, bookshelfName));
				}

				return bookshelves;
			} catch (Exception e) {
				e.printStackTrace();
				throw new BookshelfException("Sorry couldn`t load books!");
			}
		}

		return null;

	}

	public synchronized boolean validateIds(int bookshelfId, int bookId) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(
				"SELECT * FROM bookshelves_has_books WHERE bookshelves_bookshelf_id=? and books_book_id=?");
		preparedStatement.setInt(1, bookshelfId);
		preparedStatement.setInt(2, bookId);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			return true;
		} else {
			return false;
		}
	}

	private synchronized String getBookshelfNameById(int bookshefNameId) throws SQLException {
		PreparedStatement preparedStatement = connection
				.prepareStatement("SELECT* FROM bookshelf_names WHERE name_id=?");
		preparedStatement.setInt(1, bookshefNameId);
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		return resultSet.getString(2);
	}

	public synchronized Map<Book, String> getBooksFromBookShelf(int bookshelfId) throws BookException {
		Map<Book, String> booksFromBookshelf = new HashMap<Book, String>();
		try {
			if (bookshelfId > 0) {
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM bookshelves_has_books WHERE bookshelves_bookshelf_id = ?");
				preparedStatement.setInt(1, bookshelfId);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					int bookId = resultSet.getInt(2);
					String dateBookWasAdded = resultSet.getDate(3).toString();
					Book book = bookDBDAO.getBookByID(bookId);
					connection.commit();
					booksFromBookshelf.put(book, dateBookWasAdded);
				}
				return booksFromBookshelf;
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
		return booksFromBookshelf;
	}

	public synchronized Set<Bookshelf> addDefaultBookshelves(int userId) throws BookshelfException {
		if (userId != 0) {
			Set<Bookshelf> defaultBookshelves = new LinkedHashSet<>();
			try {
				getDefaultBookshelves(defaultBookshelves);
				for (Bookshelf bookshelf : defaultBookshelves) {
					addBookshelf(bookshelf.getName(), userId);
				}
			} catch (InvalidDataException | BookshelfException e) {

				e.printStackTrace();
				throw new BookshelfException();
			}
			return defaultBookshelves;
		}
		return null;
	}

	private synchronized void getDefaultBookshelves(Set<Bookshelf> defaultBookshelves) throws BookshelfException {
		try {
			connection.setAutoCommit(false);

			PreparedStatement prestatement = connection
					.prepareStatement(SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME_LIKE_READ);
			ResultSet resultSet = prestatement.executeQuery();
			int readBookshelfId = 0;
			if (!resultSet.next()) {
				prestatement = connection.prepareStatement(INSERT_INTO_BOOKSHELF_NAMES_VALUES_NULL_READ,
						Statement.RETURN_GENERATED_KEYS);
				prestatement.executeUpdate();
				resultSet = prestatement.getGeneratedKeys();
				resultSet.next();
				readBookshelfId = resultSet.getInt(1);
			} else {
				readBookshelfId = resultSet.getInt(1);
			}
			defaultBookshelves.add(new Bookshelf(readBookshelfId, "Read"));

			prestatement = connection
					.prepareStatement(SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME_LIKE_CURRENTLY_READING);
			resultSet = prestatement.executeQuery();
			int currentlyReadingBookshelfId = 0;
			if (!resultSet.next()) {
				prestatement = connection.prepareStatement(INSERT_INTO_BOOKSHELF_NAMES_VALUES_NULL_CURRENTLY_READING,
						Statement.RETURN_GENERATED_KEYS);
				prestatement.executeUpdate();
				resultSet = prestatement.getGeneratedKeys();
				resultSet.next();
				currentlyReadingBookshelfId = resultSet.getInt(1);
			} else {
				currentlyReadingBookshelfId = resultSet.getInt(1);
			}
			defaultBookshelves.add(new Bookshelf(currentlyReadingBookshelfId, "Currently-Reading"));

			prestatement = connection.prepareStatement(SELECT_FROM_BOOKSHELF_NAMES_WHERE_BOOKSHELF_NAME_LIKE_TO_READ);
			resultSet = prestatement.executeQuery();
			int toReadBookshelfId = 0;
			if (!resultSet.next()) {
				prestatement = connection.prepareStatement(INSERT_INTO_BOOKSHELF_NAMES_VALUES_NULL_TO_READ,
						Statement.RETURN_GENERATED_KEYS);
				prestatement.executeUpdate();
				resultSet = prestatement.getGeneratedKeys();
				resultSet.next();
				toReadBookshelfId = resultSet.getInt(1);
			} else {
				toReadBookshelfId = resultSet.getInt(1);
			}
			defaultBookshelves.add(new Bookshelf(toReadBookshelfId, "To-Read"));
			connection.commit();
		} catch (SQLException | InvalidDataException e) {
			e.printStackTrace();
			throw new BookshelfException("Default bookshelves not found!");
		}
	}
}
