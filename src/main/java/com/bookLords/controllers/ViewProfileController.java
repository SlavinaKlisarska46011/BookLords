package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.User;
import com.bookLords.model.daos.UserProfileDAO;
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
public class ViewProfileController implements ILogin{

	@Autowired
	private UserProfileDAO userProfileDao;

	@RequestMapping(value = "/ViewProfile", method = RequestMethod.GET)
	public String search(@ModelAttribute("loggedUser") User loggedUser, Model model, HttpServletRequest request) {
		try {
			if (loggedUser != null) {
				int id = loggedUser.getId();
				User user = userProfileDao.getUserById(id);

				if (user != null) {
					model.addAttribute("user", user);
				}
				return "viewProfile";
			}else{
				return "redirect:/Login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}
}
