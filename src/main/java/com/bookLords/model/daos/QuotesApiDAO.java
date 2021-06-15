package com.bookLords.model.daos;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

@Component
public class QuotesApiDAO {
	
	public synchronized String getQuoteOfTheDay() throws MalformedURLException, IOException {
		HttpURLConnection connection = null;

		connection = (HttpURLConnection) new URL("http://quotesondesign.com/wp-json/posts?").openConnection();

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

			JsonArray array = (JsonArray) new JsonParser().parse(json);
			JsonObject object = array.get(0).getAsJsonObject();
			String quote = object.get("content").getAsString();
			quote = quote.replace("<p>", "");
			quote = quote.replace("</p>", "");
			return quote;
		}
		return null;
	}
}
