package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.User;
import com.bookLords.model.daos.LoginDAO;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class LoginController implements ILogin {

	@Autowired
	private LoginDAO login;
	@Autowired
	UserProfileDAO userprofileDAO;

	@GetMapping(value = "/Login")
	public String sayHello(Model model) {
		model.addAttribute(new User());
		return "login";
	}

	@PostMapping(value = "/Login")
	public String register(@ModelAttribute User user, Model model, HttpServletRequest request) throws UserException {
		try {
			int id = login.validateLogin(user);
			user = userprofileDAO.getUserById(id);
			setUserSession(request, user);
			for(Bookshelf bookshelf : user.getBookshelves()){
				System.out.println(bookshelf.getId());
			}
			model.addAttribute("user", user);
			model.addAttribute("loginAgain", "");
			return "redirect:/index";
		} catch (Exception e) {
			model.addAttribute("loginAgain", "loginAgain");
			e.printStackTrace();
			return "login";
		}
	}

	@GetMapping(value = "/SignOut")
	public String logOut(Model model, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
		} catch (Exception e) {
			return "redirect:/index";
		}
		return "redirect:/index";
	}

}
