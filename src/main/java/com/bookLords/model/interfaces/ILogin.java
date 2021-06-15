package com.bookLords.model.interfaces;

import com.bookLords.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface ILogin {

	default void setUserSession(HttpServletRequest request, User user) {
		HttpSession session = request.getSession();
		session.setAttribute("loggedUser", user);
	}

	default User getCurrentUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute("loggedUser");
			return user;
		}
		return null;
	}
}
