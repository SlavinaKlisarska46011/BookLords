package com.bookLords.controllers;

import com.bookLords.model.Book;
import com.bookLords.model.Rating;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.QuotesDBDAO;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.interfaces.ILogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@Controller
@RequestMapping(value = "/")
public class HomeController implements ILogin {

    final UserProfileDAO userProfileDAO;
    final QuotesDBDAO quotesDBDAO;
    final BookDBDAO bookDBDAO;

    public HomeController(UserProfileDAO userProfileDAO, QuotesDBDAO quotesDBDAO, BookDBDAO bookDBDAO) {
        this.userProfileDAO = userProfileDAO;
        this.quotesDBDAO = quotesDBDAO;
        this.bookDBDAO = bookDBDAO;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(Model model, HttpServletRequest request) {
        try {
//            String quote = quotesDBDAO.getQuoteOfTheDay();
//            model.addAttribute("quote", quote);
            Map<Book, Double> books = new HashMap<Book, Double>();
            User user = getCurrentUser(request);
            if (user != null) {
                System.out.println("Ima user");
                user = userProfileDAO.getUserById(user.getId());
                for (User followedPerson : user.getFollowedPeople()) {
                    for (Entry<Integer, Rating> entry : followedPerson.getRatings().entrySet()) {
                        Book book = bookDBDAO.getBookByID(entry.getKey());
                        if (Math.random() > 0.8) {
                            books.put(book, book.getRating());
                        }
                    }
                }
                model.addAttribute("user", user);
                model.addAttribute("books", books);
                return "userHome";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index";
        }
        return "index";
    }
}
