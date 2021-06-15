package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Author;
import com.bookLords.model.daos.BookDBDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class ShowAuthorController {

	@Autowired
	private BookDBDAO dbDao;

	@RequestMapping(value = "/ShowAuthor", method = RequestMethod.GET)
	public String search(Model model, HttpServletRequest request) {
		try {
			String parameter = request.getParameter("id");
			int id = 0;
			if (parameter != null && !parameter.isEmpty()) {
				id = Integer.parseInt(parameter);
			}
			
			Author author = dbDao.getAuthorById(id);
			model.addAttribute("author", author);
			return "authorVisual";
		} catch (Exception e) {
			e.printStackTrace();
			return "authorVisual";
		}

	}
}
