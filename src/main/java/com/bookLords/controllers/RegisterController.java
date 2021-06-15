package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.User;
import com.bookLords.model.daos.RegisterDAO;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@ContextConfiguration(classes = BookConfig.class)
@SessionAttributes("loggedUser")
public class RegisterController implements ILogin{

	@Autowired
	private RegisterDAO registerDAO;

	@RequestMapping(value = "/Register", method = RequestMethod.GET)
	public String sayHello(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/Register", method = RequestMethod.POST)
	public String register(@ModelAttribute User user, HttpServletRequest request) throws UserException {
		try {
			User registeredUser = registerDAO.registerUser(user);
			
			setUserSession(request, registeredUser);
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "register";
		}
	}

}
