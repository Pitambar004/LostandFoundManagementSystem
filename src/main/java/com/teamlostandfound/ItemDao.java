package com.teamlostandfound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {

	public void addItem(String name, String category, String location, String date, String description, String status) throws SQLException {
		String sql = "INSERT INTO items(name, category, location, date, description, status) VALUES(?,?,?,?,?,?)";
		try (Connection connection = Database.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, name);
			ps.setString(2, category);
			ps.setString(3, location);
			ps.setString(4, date);
			ps.setString(5, description);
			ps.setString(6, status);
			ps.executeUpdate();
		}
	}

	public List<String> listItemNames() throws SQLException {
		String sql = "SELECT name FROM items ORDER BY id DESC";
		List<String> names = new ArrayList<>();
		try (Connection connection = Database.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				names.add(rs.getString("name"));
			}
		}
		return names;
	}

	public List<Item> getAllItems() throws SQLException {
		String sql = "SELECT id, name, category, location, date, description, status, contact_name, contact_phone FROM items ORDER BY id DESC";
		List<Item> items = new ArrayList<>();
		try (Connection connection = Database.getConnection(); 
			 PreparedStatement ps = connection.prepareStatement(sql); 
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String category = rs.getString("category");
				String location = rs.getString("location");
				java.sql.Date sqlDate = rs.getDate("date");
				LocalDate date = sqlDate != null ? sqlDate.toLocalDate() : null;
				String description = rs.getString("description");
				String status = rs.getString("status");
				String contactName = rs.getString("contact_name");
				String contactPhone = rs.getString("contact_phone");
				items.add(new Item(id, name, category, location, date, description, status, contactName, contactPhone));
			}
		}
		return items;
	}
}


