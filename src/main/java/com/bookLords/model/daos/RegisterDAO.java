package com.bookLords.model.daos;

import com.bookLords.model.DBConnection;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.RatingException;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.IRegisterDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;

@Component
public class RegisterDAO implements IRegisterDAO{
	private static final String DEFAULT_IMAGE = "profile.png";
	private static final String INSERT_USER_SQL = "insert into users(name,email,password,activity,profile_pic) values(?,?,md5(?),?,?)";
	@Autowired
	private BookshelvesDAO bookshelvesDAO;
	@Autowired
	private UserProfileDAO userProfileDAO;
	@Autowired
	private RatingsDAO ratingsDao;
	
	public User registerUser(User user) throws UserException{
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			LocalDate dateNow = LocalDate.now();
			String date = dateNow.toString();
			ps.setString(4, date);
			ps.setString(5, DEFAULT_IMAGE);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			BookshelvesDAO dao = new BookshelvesDAO();

			rs.next();
			int userId = rs.getInt(1);
			dao.addDefaultBookshelves(userId);
			return new User(userId, user.getName(), user.getEmail(), date, DEFAULT_IMAGE,
					userProfileDAO.getUserFollowers(userId), userProfileDAO.getUserFollowingPeople(userId),
					bookshelvesDAO.getUserBookshelves(userId), ratingsDao.getUserRatings(userId));
		} catch (SQLException | BookshelfException | RatingException | InvalidDataException e) {
			throw new UserException("User registration failed!");
		}
		// } finally {
		// try {
		// connection.close();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }
	}
}
