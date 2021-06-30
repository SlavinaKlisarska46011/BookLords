package com.bookLords.model.daos;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
public class QuotesApiDAO {
	
	public synchronized String getQuoteOfTheDay() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL("https://quotesondesign.com/wp-json/wp/v2/posts/?orderby=rand&per_page=1").openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();

		int responseCode = connection.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {

			Scanner sc = new Scanner(connection.getInputStream());

			StringBuilder builder = new StringBuilder();
			while (sc.hasNextLine()) {
				builder.append(sc.nextLine());
				builder.append("\n");
			}
			String json = builder.toString();

			JsonArray array = (JsonArray) new JsonParser().parse(json);
			JsonObject object = array.get(0).getAsJsonObject();
			String quote = object.get("content").getAsJsonObject().get("rendered").getAsString();
			quote = quote.replace("<p>", "");
			quote = quote.replace("</p>", "");
			quote = quote.replaceAll("<br />", "\n");
			quote = quote.replaceAll("&#8217;", "'");
			return quote;
		}
		return null;
	}
}
