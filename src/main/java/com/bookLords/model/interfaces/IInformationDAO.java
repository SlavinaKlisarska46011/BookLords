package com.bookLords.model.interfaces;

import com.bookLords.model.User;
import com.bookLords.model.exceptions.UserException;

public interface IInformationDAO {

	public boolean validateLogin(String email) throws UserException;
	
	public boolean validateLoginPassword(User user, String password) throws UserException;
}
