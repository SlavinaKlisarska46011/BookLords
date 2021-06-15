package com.bookLords.controllers;

import com.bookLords.configuration.BookConfig;
import com.bookLords.model.User;
import com.bookLords.model.daos.UserProfileDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Random;

@Controller
@ContextConfiguration(classes = BookConfig.class)
@SessionAttributes("loggedUser")
public class EditProfileController {

	@Autowired
	private UserProfileDAO userProfileDao;

	private static final String UPLOAD_LOCATION = "C:\\profile_pics\\";

	@RequestMapping(value = "/Edit", method = RequestMethod.GET)
	public String search(@ModelAttribute("loggedUser") User loggedUser, Model model, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession(false);
			if (session != null) {
				int id = loggedUser.getId();

				User user = userProfileDao.getUserById(id);

				if (user != null) {
					model.addAttribute("user", user);
				}
				return "edit";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
		return "redirect:/Login";
	}

	@RequestMapping(value = "/Edit", method = RequestMethod.POST)
	public String singleFileUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
			ModelMap model, @ModelAttribute("loggedUser") User loggedUser, Model model1, HttpServletRequest request,
			@RequestParam(value = "password", required = false) String password) {
		try {
			String[] path = multipartFile.getOriginalFilename().split("\\\\");
			String fileName = path[path.length - 1];
			int number = new Random().nextInt(20);
			String num = Integer.toString(number);
			fileName = num + fileName;

			int id = loggedUser.getId();

			userProfileDao.changePicture(fileName, id);

			User user = userProfileDao.getUserById(id);

			FileCopyUtils.copy(multipartFile.getBytes(), new File(UPLOAD_LOCATION + fileName));

			model1.addAttribute("user", user);

			return "edit";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/Login";
		}
	}

	@RequestMapping(value = "/EditPassword", method = RequestMethod.POST)
	public String singleFileUpload(ModelMap model, @ModelAttribute("loggedUser") User loggedUser, Model model1,
			HttpServletRequest request, @RequestParam(value = "password1", required = false) String password) {
		try {
			System.out.println(password);
			int id = loggedUser.getId();

			userProfileDao.changePassword(password, id);

			User user = userProfileDao.getUserById(id);

			model1.addAttribute("user", user);

			return "edit";
		} catch (Exception e) {
			e.printStackTrace();
			return "login";
		}
	}

}
