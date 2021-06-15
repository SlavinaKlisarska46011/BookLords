package com.bookLords.model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private static final String JDBC_MYSQL_LOCALHOST_3306_BOOKLORDS = "jdbc:mysql://127.0.0.1:3306/book_lords?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DB_PASSWORD = "148752563*";
	private static final String DB_USERNAME = "root";
	private static DBConnection instance;
	private final Connection connection;

	private DBConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		this.connection = DriverManager.getConnection(JDBC_MYSQL_LOCALHOST_3306_BOOKLORDS, DB_USERNAME, DB_PASSWORD);
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			try {
				instance = new DBConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}
