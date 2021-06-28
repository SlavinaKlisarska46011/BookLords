package com.bookLords.model.smartRecommendation;

import com.bookLords.model.Book;
import com.bookLords.model.User;
import com.bookLords.model.daos.BookDBDAO;
import com.bookLords.model.daos.UserRatingsDao;
import com.bookLords.model.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private static Map<Integer, HashMap<Book, Double>> inputData;
    private static Map<Integer, HashMap<Book, Double>> outputData = new HashMap<>();
    private static Map<Integer, List<Book>> outputDataCash = new HashMap<>();

    @PostConstruct
    public void init() throws BookException {
        inputData = userRatingsDao.getAllUsersRatings();
        System.out.println("Slope One - Before the Prediction\n");
        buildDifferencesMatrix(inputData);
        System.out.println("\nSlope One - With Predictions\n");
        Map<Integer, HashMap<Book, Double>> predict = predict(inputData);
        fillCash(predict);
    }

    public List<Book> recommend(User user) throws BookException {
        if (outputDataCash.containsKey(user.getId())){
            return outputDataCash.get(user.getId());
        }
        init();
        return outputDataCash.get(user.getId());
    }

    private void fillCash(Map<Integer, HashMap<Book, Double>> predict) {
        for (Map.Entry<Integer, HashMap<Book,Double>> entry : predict.entrySet()){
            outputDataCash.put(entry.getKey(), filterAndOrderForUser(entry.getKey(), predict));
        }
    }

    private List<Book> filterAndOrderForUser(int userId, Map<Integer, HashMap<Book, Double>> predict) {
        return predict.get(userId).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private static void buildDifferencesMatrix(Map<Integer, HashMap<Book, Double>> data) {
        for (HashMap<Book, Double> userRatings : data.values()) {
            for (Map.Entry<Book, Double> bookRating : userRatings.entrySet()) {
                Book ratingKey = bookRating.getKey();
                if (!diff.containsKey(ratingKey)) { //contains book
                    diff.put(ratingKey, new HashMap<>());
                    freq.put(ratingKey, new HashMap<>());
                }
                for (Map.Entry<Book, Double> bookRating2 : userRatings.entrySet()) {
                    int oldCount = 0;
                    Book rating2Key = bookRating2.getKey();
                    if (freq.get(ratingKey).containsKey(rating2Key)) {
                        oldCount = freq.get(ratingKey).get(rating2Key);
                    }
                    double oldDiff = 0.0;
                    if (diff.get(ratingKey).containsKey(rating2Key)) {
                        oldDiff = diff.get(ratingKey).get(rating2Key);
                    }
                    double observedDiff = bookRating.getValue() - bookRating2.getValue();
                    freq.get(ratingKey).put(rating2Key, oldCount + 1);
                    diff.get(ratingKey).put(rating2Key, oldDiff + observedDiff);
                }
            }
        }
        for (Book j : diff.keySet()) {
            for (Book i : diff.get(j).keySet()) {
                double oldValue = diff.get(j).get(i);
                int count = freq.get(j).get(i);
                diff.get(j).put(i, oldValue / count);
            }
        }
        printData(data);
    }

    /**
     * Based on existing data predict all missing ratings. If prediction is not
     * possible, the value will be equal to -1
     *
     * @param data existing user data and their books' ratings
     * @return
     */
    private Map<Integer, HashMap<Book, Double>> predict(Map<Integer, HashMap<Book, Double>> data) throws BookException {
        HashMap<Book, Double> uPred = new HashMap<>();
        HashMap<Book, Integer> uFreq = new HashMap<>();
        for (Book j : diff.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }
        for (Map.Entry<Integer, HashMap<Book, Double>> e : data.entrySet()) {
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
        printData(outputData);

        return outputData;
    }

    private static void printData(Map<Integer, HashMap<Book, Double>> data) {
        for (Integer userId : data.keySet()) {
            System.out.println(userId + ":");
            print(data.get(userId));
        }
    }

    private static void print(HashMap<Book, Double> hashMap) {
        NumberFormat formatter = new DecimalFormat("#0.000");
        for (Book j : hashMap.keySet()) {
            System.out.println(" " + j.getTitle() + " --> " + formatter.format(hashMap.get(j).doubleValue()));
        }
    }

}
