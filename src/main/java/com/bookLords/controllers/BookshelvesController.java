package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Book;
import com.bookLords.model.Bookshelf;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.BookshelvesDAO;
import com.bookLords.model.exceptions.BookshelfException;
import com.bookLords.model.interfaces.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class BookshelvesController implements ILogin {

	@Autowired
	private BookshelvesDAO bookshelvesDao;

	@Autowired
	private BookDBDAO bookDBDao;

	@GetMapping(value = "/AddBookToBookshelf")
	public @ResponseBody ResponseEntity<?> search(Model model, HttpServletRequest request) throws BookshelfException {
		try {
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			int bookshelfId = Integer.parseInt(request.getParameter("bookshelfId"));

			if (bookshelvesDao.validateIds(bookshelfId, bookId) == false) {
				if (bookshelvesDao.addBookToBookshelf(bookshelfId, bookId)) {
					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.ACCEPTED);
				}
			} else {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new BookshelfException("The book is not in the bookshelf!", e);
		}

	}

	@GetMapping(value = "/MyBooks")
	public String visualOptions(Model model, HttpServletRequest request) throws BookshelfException {
		try {
			if (request.getParameter("bookshelfId") != null) {
				int bookshelfId = Integer.parseInt(request.getParameter("bookshelfId"));
				Map<Book, String> booksFromBookshelf = bookshelvesDao.getBooksFromBookShelf(bookshelfId);
				if (booksFromBookshelf != null) {
					model.addAttribute("books", booksFromBookshelf);
				}
				return "myBooks";
			}
			model.addAttribute("bookshelf", new Bookshelf());

			return "addBookshelf";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BookshelfException("Something went wrong with the bookshelf!", e);
		}
	}

	@PostMapping(value = "MyBooks")
	public String addBookshelf(@ModelAttribute Bookshelf bookshelf, Model model, HttpServletRequest request) {
		try {
			User loggedUser = getCurrentUser(request);
			if (loggedUser != null) {
				int userId = loggedUser.getId();
				if (bookshelf.getName() == null || bookshelf.getName().isEmpty()) {
					model.addAttribute("emptyBookshelf", "empty");
					return "addBookshelf";
				}
				if (loggedUser.getBookshelves().contains(bookshelf)) {
					model.addAttribute("existingBookshelf", "exist");
					return "addBookshelf";
				}
				Bookshelf newBookshelf = bookshelvesDao.addBookshelf(bookshelf.getName(), userId);
				loggedUser.getBookshelves().add(newBookshelf);
				return "redirect:/MyBooks";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "redirect:/MyBooks";
	}
}
