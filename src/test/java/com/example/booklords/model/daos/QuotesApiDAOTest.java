package com.example.booklords.model.daos;

import com.bookLords.model.daos.QuotesApiDAO;
import org.junit.Test;

import java.io.IOException;

public class QuotesApiDAOTest {


    @Test
    public void getQuote() throws IOException {
        QuotesApiDAO quotesApiDAO = new QuotesApiDAO();
        System.out.println(quotesApiDAO.getQuoteOfTheDay());
    }
}
