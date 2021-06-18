package com.bookLords.model.daos;

import com.bookLords.model.Author;
import com.bookLords.model.Book;
import com.bookLords.model.exceptions.BookException;
import com.bookLords.model.exceptions.InvalidDataException;
import com.bookLords.model.interfaces.IBookApiDAO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

@Component
public class BookApiDAO implements IBookApiDAO{
	public LinkedHashSet<Book> getBooks(String text, BookDBDAO dao) throws MalformedURLException, IOException, InvalidDataException, BookException {
		text = text.replace(" ", "+");
		HttpURLConnection connection = null;

		connection = (HttpURLConnection) new URL("https://www.googleapis.com/books/v1/volumes?q=" + text)
				.openConnection();

		connection.connect();
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

			@SuppressWarnings("resource")
			Scanner sc = new Scanner(connection.getInputStream());

			StringBuilder builder = new StringBuilder();
			while (sc.hasNextLine()) {
				builder.append(sc.nextLine());
				builder.append("\n");
			}
			String json = builder.toString();
			JsonObject object = (JsonObject) new JsonParser().parse(json);

			int totalItems = object.get("totalItems").getAsInt();
			if (totalItems == 0) {
				System.out.println("No books found.");
				return null;
			}

			LinkedHashSet<Book> books = new LinkedHashSet<Book>();
			JsonArray jsonArray = object.get("items").getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonObject book = (JsonObject) jsonArray.get(i).getAsJsonObject();
				JsonObject bookVolumeInfo = book.get("volumeInfo").getAsJsonObject();
				String title = checkIfElementExists(bookVolumeInfo, "title");
				JsonArray bookAuthors;
				Set<Author> authors = new LinkedHashSet<Author>();
				if (bookVolumeInfo.get("authors") != null) {
					bookAuthors = bookVolumeInfo.get("authors").getAsJsonArray();
					for (JsonElement author : bookAuthors) {
						authors.add(new Author(author.toString()));
					}
				}

				String publisher = checkIfElementExists(bookVolumeInfo, "publisher");
				String publishedDate = checkIfElementExists(bookVolumeInfo, "publishedDate");

				String description = checkIfElementExists(bookVolumeInfo, "description");

				// ISBN:
				String isbn = "";
				if (bookVolumeInfo.get("industryIdentifiers") != null
						&& bookVolumeInfo.get("industryIdentifiers").isJsonArray()) {
					JsonArray industryIdentifiers = bookVolumeInfo.get("industryIdentifiers").getAsJsonArray();
					JsonObject ISBN = industryIdentifiers.get(0).getAsJsonObject();
					String type = checkIfElementExists(ISBN, "type");

					String identifier = checkIfElementExists(ISBN, "identifier");
					isbn = type + ": " + identifier;
				}
				// JsonObject ISBN13 =
				// industryIdentifiers.get(1).getAsJsonObject();
				// String type13 = ISBN13.get("type").getAsString();
				// String identifier13 = ISBN13.get("identifier").getAsString();
				// pages:
				int pages = 0;
				if (bookVolumeInfo.get("pageCount") != null) {
					pages = bookVolumeInfo.get("pageCount").getAsInt();
				}
				// can get averageRating and ratingsCount;
				String genres = checkIfElementExists(bookVolumeInfo, "categories");

				JsonObject imageUrl = bookVolumeInfo;
				String smallImage = "";
				if (bookVolumeInfo.get("imageLinks") != null) {
					imageUrl = bookVolumeInfo.get("imageLinks").getAsJsonObject();
					smallImage = checkIfElementExists(imageUrl, "smallThumbnail");

				}
				String language = checkIfElementExists(bookVolumeInfo, "language");

				JsonObject access = book.get("accessInfo").getAsJsonObject();
				String readOnlineURL = checkIfElementExists(access, "webReaderLink");
				String buyOnlineURL = checkIfElementExists(access, "buyLink");

				books.add(new Book(title, authors, publisher, publishedDate, description, isbn, pages, genres,
						smallImage, language, readOnlineURL, buyOnlineURL));

			}

			dao.addBooks(books);

			return books;
		}
		return null;
	}

	private static String checkIfElementExists(JsonObject obj, String elementText) {
		if (obj.get(elementText) != null) {
			return obj.get(elementText).getAsString();
		}
		return "none";
	}

}
