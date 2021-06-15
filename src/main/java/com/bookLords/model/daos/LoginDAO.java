package com.bookLords.model.daos;

import com.bookLords.model.DBConnection;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.ILoginDAO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class LoginDAO implements ILoginDAO{

	private static final String LOGIN_USER_SQL = "SELECT user_id FROM users WHERE email = ? AND password = md5(?)";

	public int validateLogin(User user)
			throws SQLException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement ps = 
					connection.prepareStatement(LOGIN_USER_SQL);
			ps.setString(1, user.getEmail());	
			ps.setString(2, user.getPassword());
			ResultSet rs = ps.executeQuery();
			if (rs.next() == false) {
				throw new UserException("User login failed!");
			}
			
			return rs.getInt(1);
		}
		catch (Exception e) {
			throw new UserException("Something went wrong with login!",e);
		}
	}
}
