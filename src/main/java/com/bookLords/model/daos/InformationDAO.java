package com.bookLords.model.daos;

import com.bookLords.model.DBConnection;
import com.bookLords.model.User;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.IInformationDAO;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class InformationDAO implements IInformationDAO{

	private static final String CHECK_USER_SQL = "select * from users where email = ?;";
	
	private static final String CHECK_USER_PASSWORD = "SELECT * FROM users where user_id = ? and password = md5(?);";
	
	public boolean validateLogin(String email) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();
				
		try {
			PreparedStatement ps = 
					connection.prepareStatement(CHECK_USER_SQL);
			ps.setString(1, email);	
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}else{
				return true;
			}

		}
		catch (Exception e) {
			throw new UserException("Something went wrong with login!",e);
		}
	}
	
	public boolean validateLoginPassword(User user, String password) throws UserException {
		Connection connection = DBConnection.getInstance().getConnection();
				
		try {
			PreparedStatement ps = 
					connection.prepareStatement(CHECK_USER_PASSWORD);
			ps.setInt(1, user.getId());	
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}else{
				return false;
			}


		}
		catch (Exception e) {
			throw new UserException("Something went wrong with checking the password!",e);
		}
	}
}
