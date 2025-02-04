package com.bptn.expense_tracker;

//Import necessary packages
import java.sql.*;
import java.util.*;

public class DatabaseHandler {
	private static final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	
	private Connection connection;

	
	//Connect to the database
	public Connection connect() {
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Database connected successfully");
			return connection;
		} catch (SQLException e) {
			System.err.println("Database connection failed" + e.getMessage());
			return null;
		}
		
	}
	
	//Close the database
	public void close() {	
			try {
				if (connection != null) {
				connection.close();
				System.out.println("Database connection closed successfully");
				}
			} catch (SQLException e) {
				System.err.println("Error closing database" + e.getMessage());
			}
				
	}
	
	//Method to create tables if they don't exist
	public void createTable() {
		String users = "CREATE TABLE IF NOT EXISTS users ("
				+ "userId INT AUTO_INCREMENT PRIMARY KEY," 
				+ "username VARCHAR(50) NOT NULL UNIQUE,"
				+ "email VARCHAR(200) NOT NULL UNIQUE,"
				+ "password_hash VARCHAR(255) NOT NULL,"
				+ "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
		
		String categories = "CREATE TABLE IF NOT EXISTS categories ("
				+ "categoryId INT AUTO_INCREMENT PRIMARY KEY," 
				+ "categoryName VARCHAR(50) NOT NULL UNIQUE)";
		
		String expenses = "CREATE TABLE IF NOT EXISTS expenses ("
				+ "expenseId INT AUTO_INCREMENT PRIMARY KEY," 
				+ "userId INT NOT NULL," 
				+ "categoryId INT NOT NULL," 
				+ "amount DECIMAL(10,2) NOT NULL,"
				+ "description VARCHAR(100),"
				+ "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
				+ "FOREIGN KEY (userId) REFERENCES users(userId) ON DELETE CASCADE,"
				+ "FOREIGN KEY (categoryId) REFERENCES categories(categoryId) ON DELETE CASCADE)";
		
		
		
		try(Statement stmt = connection.createStatement()){
			stmt.execute(users);
			stmt.execute(categories);
			stmt.execute(expenses);
			
			System.out.println("Tables created successfully");
		} catch (SQLException e) {
			System.err.println("Error creating tables" + e.getMessage());
		}
		
	}
	
	//Method to add new user to database
	public boolean addUser(String username, String email, String password_hash) {
		String sql = "INSERT INTO users (username, email, password_hash) VALUES (?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, username);
			stmt.setString(2, email);
			stmt.setString(3, password_hash);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Error adding user: " + e.getMessage());
			return false;
		}
		
		
	}
	
	// Method to check if the username or email already exist in the database
	public boolean checkUserExists(String username) {
		String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();	
			if (rs.next() && rs.getInt(1) > 0) {
				System.out.println("Error: Username already exist.");
				return false;
			}
			
		} catch (SQLException e){
			System.out.println("Error checking existing user: " + e.getMessage());
			return false;
		}
		return true;
	
	}
	
	// Method to check if the username or email already exist in the database
		public boolean checkEmailExists(String email) {
			String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
			try (PreparedStatement stmt = connection.prepareStatement(sql)){
			
				stmt.setString(1,  email);
				ResultSet rs = stmt.executeQuery();	
				if (rs.next() && rs.getInt(1) > 0) {
					System.out.println("Error: Email already exist.");
					return false;
				}			
			} catch (SQLException e){
				System.out.println("Error checking existing user: " + e.getMessage());
				return false;
			}
			return true;
		
		}
	
	// Method that fetches user details based on the username
	public String getUserByUsername(String username) {
		String sql = "SELECT email FROM users WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("email");
			}
			
		} catch (SQLException e) {
			System.out.println("Error fetching user: " + e.getMessage());
			
		}
		return null;
		
	}
	
	// Method to add expense
	public boolean addExpense(int userId, double amount, String categoryName, String description) {
		int categoryId = getCategoryIdByName(categoryName);
		
		if (categoryId == -1) {
			System.err.println("Invalid category");
			return false;
		}
		String sql = "INSERT INTO expenses (userId, category_id, amount, description) values (?,?,?,?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, userId);
			stmt.setInt(2, categoryId);
			stmt.setDouble(3, amount);
			stmt.setString(4, description);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Error adding expense: " + e.getMessage());
			return false;
		}
	}
	
	// Method to get expenses by user
	public void getExpenseByUser(int userId){
		String sql = "SELECT * FROM expenses WHERE userId = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Expense ID: " + rs.getInt("expenseId") 
				+ "\n Amount: " + rs.getDouble("amount")
				+ "\n Category: " + rs.getString("category")
				+ "\n Description: " + rs.getString("description"));
			}
			
		} catch (SQLException e) {
			System.out.println("Error retrieving expense data: " + e.getMessage());
			
		}
		
	}
	
	// Method to delete an expense by Id
	public boolean deleteExpense(int expenseId) {
		String sql = "DELETE FROM expenses WHERE expenseId = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, expenseId);
			stmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			System.out.println("Error deleting expense: " + e.getMessage());
			return false;
		}
	}
	
	// Method to add default categories to the table
	public boolean addCategory(String categoryName) {
		// Check if category already exist
		if (getCategoryIdByName(categoryName) != -1) {
			System.out.println("Category already exists!");
			return false;
		}
		String sql = "INSERT INTO categories (categoryName) VALUES "
				+ "(?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, categoryName);
			stmt.executeUpdate();
			System.out.println("Category added successfully!");
			return true;
		} catch (SQLException e) {
			System.out.println("Error adding category: " + e.getMessage());
			return false;
		}
			
	}
	
	// Fetch categories data from the database
	public List<String> getAllCategories(){
		List<String> categories = new ArrayList<>();
		String sql = "SELECT categoryName FROM categories";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("categoryName"));
			}
			
		} catch (SQLException e) {
			System.out.println("Error fetching categories: " + e.getMessage());	
		}
		return categories;
		
	}
	
	// Function to get category Id by name
	private int getCategoryIdByName(String categoryName) {
		String sql = "SELECT categoryId from CATEGORIES WHERE categoryName = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, categoryName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				rs.getInt("categoryId");
			}
			
		} catch (SQLException e) {
			System.out.println("Error retrieving category Id: " + e.getMessage());	
		}
		return -1;
	}
	
	
}
