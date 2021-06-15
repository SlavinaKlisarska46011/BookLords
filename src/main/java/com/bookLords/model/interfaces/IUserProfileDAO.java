package com.bookLords.model.interfaces;

import com.bookLords.model.User;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.UserException;

import java.sql.SQLException;
import java.util.Set;

public interface IUserProfileDAO {

	public void changePicture(String picture, int userId) throws SQLException, InvalidDataException;
	
	public void changePassword(String password, int userId) throws SQLException, InvalidDataException;
	
	public void follow(int follower, int userId) throws UserException;
	
	public Set<User> getUserFollowingPeople(int userId) throws UserException;
	
	public Set<User> getUserFollowers(int userId) throws UserException;
	
	public void unfollow(int id, int unfollowUserId) throws UserException;
}
