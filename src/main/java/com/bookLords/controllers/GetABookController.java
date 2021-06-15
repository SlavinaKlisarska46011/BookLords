package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Book;
import com.bookLords.model.Comment;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.CommentsDAO;
import com.bookLords.model.daos.RatingsDAO;
import com.bookLords.model.exceptions.UserException;
import com.bookLords.model.interfaces.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@ContextConfiguration(classes = BookConfig.class)
public class GetABookController implements ILogin {

	@Autowired
	private BookDBDAO bookDBDao;
	@Autowired
	private CommentsDAO commentsDBDao;
	@Autowired
	private RatingsDAO ratingsDBDAO;

	@GetMapping(value = "/ShowBook")
	public String search(Model model, HttpServletRequest request, Comment comment) {
		try {
			Integer bookId = Integer.parseInt(request.getParameter("id"));
			Book book = bookDBDao.getBookByID(bookId);
			int rating = 0;
			if (request.getParameter("rating") != null) {
				rating = Integer.parseInt(request.getParameter("rating"));
			}

			User user = getCurrentUser(request);
			if (user != null) {
				if (user.getRatings().containsKey(bookId)) {
					model.addAttribute("rated", user.getRatings().get(bookId).getRating());
				} else {

					if (rating >= 1 && rating <= 5) {
						
							ratingsDBDAO.updateRatings(user, book, rating, LocalDateTime.now());
							model.addAttribute("rated", user.getRatings().get(bookId).getRating());
						}
					}
				}
			

			if (book != null) {
				model.addAttribute("book", book);
			}
			return "bookVisual";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}

	@PostMapping(value = "/ShowBook")
	public String register(@ModelAttribute Comment comment, Model model, HttpServletRequest request)
			throws UserException {
		try {
			if (comment.getText().isEmpty()) {
				return "bookVisual";
			}

			User user = getCurrentUser(request);
			if (user != null) {
				comment.setUser(user);
				comment.setDate(LocalDate.now().toString());
				commentsDBDao.addComment(comment);

				Book book = bookDBDao.getBookByID(comment.getBookId());
				book.getComments().add(comment);
				model.addAttribute("book", book);
				return search(model, request, comment);

			}
			return "redirect:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}
}
