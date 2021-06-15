package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.User;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.interfaces.ILogin;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@ContextConfiguration(classes = BookConfig.class)
@SessionAttributes("loggedUser")
public class PeopleSearchController implements ILogin {

	@Autowired
	private UserProfileDAO userDao;

	@RequestMapping(value = "/SearchPeople", method = RequestMethod.GET)
	public String sayHello(Model model, HttpServletRequest request) {
		return "userVisual";
	}

	@RequestMapping(value = "/SearchPeopleByName", method = RequestMethod.GET)
	protected void getUsersByName(@ModelAttribute("loggedUser") User loggedUser, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Set<User> users = new LinkedHashSet<User>();
		try {
			int id = loggedUser.getId();
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");

			String prefix = request.getParameter("prefix");
			users = userDao.getUsersByName(prefix, id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		response.getWriter().print(new Gson().toJson(users));
	}

	@RequestMapping(value = "/searchByName", method = RequestMethod.POST)
	public String searchBy(@ModelAttribute("loggedUser") User loggedUser, Model model, HttpServletRequest request) {
		Set<User> users = new LinkedHashSet<User>();
		try {
			int id = loggedUser.getId();
			String searchName = request.getParameter("searchName");

			users = userDao.getUsersByName(searchName, id);

			User user = getCurrentUser(request);
			if (user != null) {
				model.addAttribute("user", user);
			}
			model.addAttribute("users", users);
			return "userVisual";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}
}
