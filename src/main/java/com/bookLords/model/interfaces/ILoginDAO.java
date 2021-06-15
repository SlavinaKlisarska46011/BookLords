package com.bookLords.model.interfaces;

import com.bookLords.model.User;
import com.bookLords.model.exceptions.UserException;

import java.sql.SQLException;

public interface ILoginDAO {

	public int validateLogin(User user) throws SQLException, UserException;
}
