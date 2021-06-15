package com.bookLords.model.interfaces;

import com.bookLords.model.User;
import com.bookLords.model.exceptions.UserException;

public interface IRegisterDAO {
	
	public User registerUser(User user) throws UserException;
}
