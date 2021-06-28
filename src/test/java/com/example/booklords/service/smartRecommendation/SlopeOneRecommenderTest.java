package com.example.booklords.service.smartRecommendation;

import com.bookLords.configuration.BookConfig;
import com.bookLords.configuration.SpringWebConfig;
import com.bookLords.model.daos.UserProfileDAO;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.smartRecommendation.SlopeOneRecommender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {SpringWebConfig.class, BookConfig.class})
public class SlopeOneRecommenderTest {

    @Autowired
    private SlopeOneRecommender slopeOneRecommender;
    @Autowired
    private UserProfileDAO userProfileDAO;

    @Test
    public void testRecommendation() throws SQLException, InvalidDataException, BookException {
        slopeOneRecommender.recommend(userProfileDAO.getUserById(26));
    }
}
