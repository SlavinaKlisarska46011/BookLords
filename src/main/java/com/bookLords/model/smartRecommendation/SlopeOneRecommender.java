package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.UserRatingsDao;
import com.bookLords.model.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SlopeOneRecommender implements Recommender {

    @Autowired
    BookDBDAO bookDBDAO;
    @Autowired
    UserRatingsDao userRatingsDao;

    private static Map<Book, Map<Book, Double>> diff = new HashMap<>();
    private static Map<Book, Map<Book, Integer>> freq = new HashMap<>();
    private static Map<User, HashMap<Book, Double>> inputData;
    private static Map<User, HashMap<Book, Double>> outputData = new HashMap<>();

    public List<Book> recommend(User user) throws BookException {
        inputData = userRatingsDao.getAllUsersRatings();
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        Map<User, HashMap<Book, Double>> predict = predict(inputData);

        return filterAndOrderForUser(user, predict);
    }

    private List<Book> filterAndOrderForUser(User user, Map<User, HashMap<Book, Double>> predict) {
        return predict.get(user).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static void buildDifferencesMatrix(Map<User, HashMap<Book, Double>> data) {
        for (HashMap<Book, Double> userRatings : data.values()) {
            for (Map.Entry<Book, Double> bookRating : userRatings.entrySet()) {
                if (!diff.containsKey(bookRating.getKey())) { //contains book
                    diff.put(bookRating.getKey(), new HashMap<>());
                    freq.put(bookRating.getKey(), new HashMap<>());
                }
                for (Map.Entry<Book, Double> bookRating2 : userRatings.entrySet()) {
                    int oldCount = 0;
                    if (freq.get(bookRating2.getKey()).containsKey(bookRating2.getKey())) {
                        oldCount = freq.get(bookRating2.getKey()).get(bookRating2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (diff.get(bookRating2.getKey()).containsKey(bookRating2.getKey())) {
                        oldDiff = diff.get(bookRating2.getKey()).get(bookRating2.getKey()).doubleValue();
                    }
                    double observedDiff = bookRating2.getValue() - bookRating2.getValue();
                    freq.get(bookRating2.getKey()).put(bookRating2.getKey(), oldCount + 1);
                    diff.get(bookRating2.getKey()).put(bookRating2.getKey(), oldDiff + observedDiff);
                }
            }
        }
        for (Book j : diff.keySet()) {
            for (Book i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i).doubleValue();
                int count = freq.get(j).get(i).intValue();
                diff.get(j).put(i, oldValue / count);
            }
        }
//        printData(data);
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     *
     * @param data existing user data and their books' ratings
     * @return
     */
    private Map<User, HashMap<Book, Double>> predict(Map<User, HashMap<Book, Double>> data) throws BookException {
        HashMap<Book, Double> uPred = new HashMap<>();
        HashMap<Book, Integer> uFreq = new HashMap<>();
        for (Book j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Map.Entry<User, HashMap<Book, Double>> e : data.entrySet()) {
            for (Book j : e.getValue().keySet()) {
                for (Book k : diff.keySet()) {
                    try {
                        double predictedValue = diff.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * freq.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + freq.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }
            HashMap<Book, Double> clean = new HashMap<>();
            for (Book j : uPred.keySet()) {
                if (uFreq.get(j) > 0) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (Book j : bookDBDAO.getAllBooks()) {
                if (e.getValue().containsKey(j)) {
                    clean.put(j, e.getValue().get(j));
                } else if (!clean.containsKey(j)) {
//                    clean.put(j, -1.0);
                }
            }
            outputData.put(e.getKey(), clean);
        }
//        printData(outputData);

        return outputData;
    }

    private static void printData(Map<User, HashMap<Book, Double>> data) {
        for (User user : data.keySet()) {
            System.out.println(user.getName() + ":");
            print(data.get(user));
        }
    }

    private static void print(HashMap<Book, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Book j : hashMap.keySet()) {
            System.out.println(" " + j.getTitle() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

}
