package com.bookLords.model;

import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.interfaces.IUser;
import com.bookLords.model.validation.EmailValidator;
import com.bookLords.model.validation.PasswordValidator;

import java.util.*;

public class User implements IUser {

    private int id;
    private String name;
    private String email;
    private String password;
    private String activity;
    private String profilePicture;

    private Set<Bookshelf> bookshelves = new LinkedHashSet<Bookshelf>();

    // Sort by date added!!(else by id)
    private Map<Integer, Rating> ratings = new HashMap<>();

    // TreeSet to be sorted alphabetically:
    private Set<User> followedPeople = new TreeSet<User>(getUserComparator());
    private Set<User> followers = new TreeSet<User>(getUserComparator());

    private final List<Book> recommendationsByContend = new ArrayList<>();
    private final List<Book> recommendationsBySlope = new ArrayList<>();

    public User() {
    }

    public Comparator<User> getUserComparator() {
        return (u1, u2) -> (u1.getId() - u2.getId());
    }

    public Comparator<Rating> getRatingComparator() {
        return (r1, r2) -> (r1.getId() - r2.getId());
    }

    public User(int id, String name, String activity, String profilePicture) {
        this.id = id;
        this.name = name;
        this.activity = activity;
        this.profilePicture = profilePicture;
    }


    public User(int id, String name, String email, String password, String activity, String profilePicture)
            throws InvalidDataException {
        isValidString(name);
        this.id = id;
        if (checkEmail(email)) {
            this.email = email;
        } else {
            throw new InvalidDataException("Invalid email!");
        }

        if (checkThePassword(password)) {
            this.password = password;
        } else {
            throw new InvalidDataException("Invalid password!");
        }

        this.activity = activity;

        this.profilePicture = profilePicture;
    }

    public User(int id, String name, String profilePicture, Map<Integer, Rating> userRatings) {
        this.id = id;
        this.name = name;
        this.profilePicture = profilePicture;
        this.ratings = userRatings;
    }

    public User(int userId, String name, String email, String activity, String profilePicture, Set<User> userFollowers,
                Set<User> userFollowingPeople, Set<Bookshelf> userBookshelves, Map<Integer, Rating> ratings) {
        this.id = userId;
        this.name = name;
        this.email = email;
        this.activity = activity;
        this.profilePicture = profilePicture;
        this.followers.addAll(userFollowers);
        this.followedPeople.addAll(userFollowingPeople);
        this.bookshelves = userBookshelves;
        this.ratings = ratings;
    }

    public String getActivity() {
        return activity;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    @Override
    public void isValidString(String string) throws InvalidDataException {
        if (string != null && string.trim().length() > 0) {
            this.name = string;
        } else {
            throw new InvalidDataException("Invalid name!");
        }
    }

    @Override
    public boolean checkEmail(String email) {
        EmailValidator emailValidator = new EmailValidator();
        if (emailValidator.validate(email)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkThePassword(String password) {
        PasswordValidator passwordValidator = new PasswordValidator();
        if (passwordValidator.validate(password)) {
            return true;
        }
        return false;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;

    }

    // Return a copy of the collection to avoid the client possibility to modify
    // it
    public Set<Bookshelf> getBookshelves() {
        //Set<Bookshelf> bookshelves = new LinkedHashSet<Bookshelf>(this.bookshelves);
        return bookshelves;
    }

    // Return a copy of the collection to avoid the client possibility to modify
    // it
    public Set<User> getFollowedPeople() {
//		Set<User> followedPeople = new TreeSet<User>(getUserComparator());
//		followedPeople.addAll(this.followedPeople);
        return followedPeople;
    }

    // Return a copy of the collection to avoid the client possibility to modify
    // it
    public Set<User> getFollowers() {
//		Set<User> followers = new TreeSet<User>(getUserComparator());
//		followers.addAll(this.followers);
        return followers;
    }


    public Map<Integer, Rating> getRatings() {
        return ratings;
    }

    public void addRecommendationsBySlope(List<Book> recommendations) {
        recommendationsBySlope.addAll(recommendations);
    }

    public List<Book> getRecommendationsBySlope() {
        return Collections.unmodifiableList(recommendationsBySlope);
    }

    public void addRecommendationsByContent(List<Book> recommendations) {
        recommendationsByContend.addAll(recommendations);
    }

    public List<Book> getRecommendationsByContent() {
        return Collections.unmodifiableList(recommendationsByContend);
    }

    public Object getRecommendations() {
        return this.getRatings().size() > 0 ? recommendationsByContend : recommendationsBySlope;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
