package com.bookLords.model.daos;

import com.bookLords.model.DBConnection;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.*;
import com.bookLords.model.interfaces.IUserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class UserProfileDAO implements IUserProfileDAO{
	private static Connection connection = DBConnection.getInstance().getConnection();

	@Autowired
	private BookshelvesDAO bookshelvesDAO;
	@Autowired
	private RatingsDAO ratingsDao;

	private static final String SELECT_FROM_USERS_WHERE_NAME_LIKE = "SELECT * FROM users where name like \'";

	public synchronized User getUserById(int userId) throws SQLException, InvalidDataException {
		try {
			String selectSQL = "SELECT * FROM users where user_id = ?;";
			PreparedStatement ps = connection.prepareStatement(selectSQL);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String activity = rs.getString("activity");
				String profilePicture1 = rs.getString("profile_pic");
				String profilePicture = "./profile_pics/" + profilePicture1;

				return new User(userId, name, email, activity, profilePicture, getUserFollowers(userId),
						getUserFollowingPeople(userId), bookshelvesDAO.getUserBookshelves(userId),
						ratingsDao.getUserRatings(userId));
			}
		} catch (UserException | BookshelfException | RatingException e) {

			e.printStackTrace();
		}
		return null;
	}

	public synchronized User getUserInfoById(int userId) throws UserException {
		try {
			String selectSQL = "SELECT * FROM users where user_id = ?;";
			PreparedStatement ps = connection.prepareStatement(selectSQL);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String profilePicture1 = rs.getString("profile_pic");
				String profilePicture = "./profile_pics/" + profilePicture1;
				return new User(userId, name, profilePicture,ratingsDao.getUserRatings(userId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException();
		}
		return null;
	}

	public void changePicture(String picture, int userId) throws SQLException, InvalidDataException {
		String selectSQL = "update users set profile_pic = ? where user_id = ?";
		PreparedStatement ps = connection.prepareStatement(selectSQL);
		ps.setString(1, picture);
		ps.setInt(2, userId);
		ps.executeUpdate();

	}

	public void changePassword(String password, int userId) throws SQLException, InvalidDataException {
		String selectSQL = "update users set password = md5(?) where user_id = ?;";
		PreparedStatement ps = connection.prepareStatement(selectSQL);
		ps.setString(1, password);
		ps.setInt(2, userId);
		ps.executeUpdate();
	}

	public synchronized Set<User> getUsersByName(String namePrefix, int id) throws BookException {
		Set<User> users = new LinkedHashSet<User>();
		try {
			if (namePrefix != null && !namePrefix.equals("")) {
				if (!(namePrefix.contains("\'") || namePrefix.contains("\""))) {
					connection = DBConnection.getInstance().getConnection();
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement
							.executeQuery(SELECT_FROM_USERS_WHERE_NAME_LIKE + namePrefix + "%\';");

					while (resultSet.next()) {
						int userId = resultSet.getInt("user_id");
						String name = resultSet.getString("name");
						String activity = resultSet.getString("activity");
						String profilePicture = resultSet.getString("profile_pic");

						if (userId != id) {
							users.add(new User(userId, name, activity, profilePicture));
						}
					}
					return users;
				} else {
					throw new BookException();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void follow(int follower, int userId) throws UserException {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("INSERT INTO followers VALUES(?,?)");

			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, follower);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException();
		}
	}

	public Set<User> getUserFollowingPeople(int userId) throws UserException {
		Set<User> followingPeople = new HashSet<User>();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM followers WHERE follower = ?",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int followedUserId = resultSet.getInt(1);
				User user = getUserInfoById(followedUserId);
				followingPeople.add(user);
			}
			return followingPeople;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException();
		}
	}

	public Set<User> getUserFollowers(int userId) throws UserException {
		Set<User> followers = new HashSet<User>();
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM followers WHERE being_followed = ?",
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int follower = resultSet.getInt(2);
				User user = getUserInfoById(follower);
				followers.add(user);
			}
			return followers;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException();
		}
	}

	public void unfollow(int id, int unfollowUserId) throws UserException {
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection
					.prepareStatement("delete from followers where being_followed = ? AND follower = ?");

			preparedStatement.setInt(1, unfollowUserId);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException();
		}
	}
}
