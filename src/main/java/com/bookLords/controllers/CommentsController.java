package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.daos.CommentsDAO;
import com.bookLords.model.daos.UserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@ContextConfiguration(classes = BookConfig.class)
@SessionAttributes("loggedUser")
public class CommentsController {
	
	@Autowired
	private UserProfileDAO userProfileDao;
	
	@Autowired
	private CommentsDAO commentsDao;

	
	@RequestMapping(value = "/MyComments", method = RequestMethod.GET)
	public String searchComments(@ModelAttribute("loggedUser") User loggedUser, Model model, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				int id = loggedUser.getId();

				Map<Book, String> booksWithComments = commentsDao.getCommentsById(id);
				if (booksWithComments != null) {
					model.addAttribute("booksWithComments", booksWithComments);
				}
				return "myComments";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "redirect:/Login";
	}

}
