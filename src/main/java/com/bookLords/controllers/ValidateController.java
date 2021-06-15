package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.User;
import com.bookLords.model.daos.InformationDAO;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.exceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@ContextConfiguration(classes = BookConfig.class)
@SessionAttributes("loggedUser")
public class ValidateController {

	@Autowired
	private InformationDAO infoDAO;

	@Autowired
	private UserProfileDAO userProfileDao;

	@RequestMapping(value = "/Validate", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> validatingFunction(@RequestParam("email") String email)
			throws UserException {
		try {
			if (infoDAO.validateLogin(email)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
		} catch (UserException e) {
			e.printStackTrace();
			throw new UserException("Nevalidni danni!", e);
		}
	}

	@RequestMapping(value = "/Validate", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<?> validatingPassword(@ModelAttribute("loggedUser") User loggedUser,
			@RequestParam("password") String password) throws UserException, SQLException, InvalidDataException {
		try {
			int id = loggedUser.getId();

			User user = userProfileDao.getUserById(id);

			if (infoDAO.validateLoginPassword(user, password)) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
		} catch (UserException e) {
			e.printStackTrace();
			throw new UserException("Nevalidni danni!", e);
		}
	}

}
