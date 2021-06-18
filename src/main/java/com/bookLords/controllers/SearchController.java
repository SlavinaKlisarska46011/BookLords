package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookApiDAO;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.interfaces.ILogin;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class SearchController implements ILogin {

	@Autowired
	private BookDBDAO dbDao;

	@Autowired
	private BookApiDAO apiDao;

	@GetMapping(value = "/Search")
	protected void getBooksByPrefix(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Set<Book> books;
		try {
			response.setContentType("text/json");
			response.setCharacterEncoding("UTF-8");

			String prefix = request.getParameter("prefix");
			books = dbDao.getBooksByTitle(prefix);

			response.getWriter().print(new Gson().toJson(books));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@GetMapping(value = "/search")
	public String searchBooksInDB(Model model, HttpServletRequest request) {
		Set<Book> books;
		try {
			String search = request.getParameter("search");
			String searchBy = request.getParameter("searchBy");
			if (searchBy != null) {
				switch (searchBy) {

				case "author":
					books = dbDao.getBooksByAuthor(search);
					break;
				case "genre":
					books = dbDao.getBooksByGenre(search);
					break;
				default:
					books = dbDao.getBooksByTitle(search);
					break;
				}
			} else {
				books = dbDao.getBooksByTitle(search);
			}
			
			if (books.isEmpty()) {
				books.addAll(apiDao.getBooks(search, dbDao));
			}

			User user = getCurrentUser(request);
			if (user != null) {
				model.addAttribute("user", user);
			}
			if (books != null) {
				model.addAttribute("books", books);
			}
			return "search";
		} catch (Exception e) {
			e.printStackTrace();
			return "search";
		}
	}

	@PostMapping(value = "/search")
	public String searchBy(Model model, HttpServletRequest request) throws BookshelfException {
		return searchBooksInDB(model, request);

	}

}
