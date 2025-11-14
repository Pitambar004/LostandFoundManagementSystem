package com.teamlostandfound;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {

	// Railway MySQL Database Credentials
	private static final String DEFAULT_HOST = "ballast.proxy.rlwy.net";
	private static final String DEFAULT_PORT = "37090";
	private static final String DEFAULT_DATABASE = "railway";
	private static final String DEFAULT_USERNAME = "root";
	private static final String DEFAULT_PASSWORD = "BEzjfVYRMfPYqlUOBRrBkpHMoKuHJOyi";
	
	private static String host = DEFAULT_HOST;
	private static String port = DEFAULT_PORT;
	private static String database = DEFAULT_DATABASE;
	private static String username = DEFAULT_USERNAME;
	private static String password = DEFAULT_PASSWORD;

	private Database() {}

	public static void configure(String host, String port, String database, String username, String password) {
		if (host != null && !host.isBlank()) Database.host = host;
		if (port != null && !port.isBlank()) Database.port = port;
		if (database != null && !database.isBlank()) Database.database = database;
		if (username != null && !username.isBlank()) Database.username = username;
		if (password != null) Database.password = password;
	}

	public static Connection getConnection() throws SQLException {
		String jdbcUrl = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", 
			host, port, database);
		return DriverManager.getConnection(jdbcUrl, username, password);
	}

	public static void initialize() throws SQLException {
		try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
			statement.executeUpdate(
				"CREATE TABLE IF NOT EXISTS users (" +
				"id INT AUTO_INCREMENT PRIMARY KEY, " +
				"username VARCHAR(50) UNIQUE NOT NULL, " +
				"password_hash VARCHAR(255) NOT NULL, " +
				"role VARCHAR(20) NOT NULL DEFAULT 'ADMIN'" +
				")");

			statement.executeUpdate(
				"CREATE TABLE IF NOT EXISTS items (" +
				"id INT AUTO_INCREMENT PRIMARY KEY, " +
				"name VARCHAR(100) NOT NULL, " +
				"category VARCHAR(50), " +
				"location VARCHAR(100), " +
				"date DATE, " +
				"description TEXT, " +
				"status VARCHAR(20) NOT NULL DEFAULT 'FOUND', " +
				"contact_name VARCHAR(100), " +
				"contact_phone VARCHAR(10)" +
				")");
			
			// Add new columns if they don't exist (for existing databases)
			try {
				java.sql.DatabaseMetaData metaData = connection.getMetaData();
				java.sql.ResultSet columns = metaData.getColumns(null, null, "items", "contact_name");
				if (!columns.next()) {
					statement.executeUpdate("ALTER TABLE items ADD COLUMN contact_name VARCHAR(100)");
				}
				columns.close();
			} catch (SQLException e) {
				// Column might already exist, ignore
			}
			try {
				java.sql.DatabaseMetaData metaData = connection.getMetaData();
				java.sql.ResultSet columns = metaData.getColumns(null, null, "items", "contact_phone");
				if (!columns.next()) {
					statement.executeUpdate("ALTER TABLE items ADD COLUMN contact_phone VARCHAR(10)");
				}
				columns.close();
			} catch (SQLException e) {
				// Column might already exist, ignore
			}
		}
	}
}


