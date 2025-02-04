package com.bptn.expense_tracker;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.EmptyStackException;
import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.bptn.expense_tracker.exceptions.EmptyFieldException;

public class User {

	DatabaseHandler db = new DatabaseHandler();

	// Declare variables
	private String username;
	private String password;
	static int userId = 100;
	private String email;
	private String role;
	public static Scanner scanner = new Scanner(System.in);

	// Default constructor
	public User() {

	}

	// Parameterized Constructor
	public User(String username, String password, String email, String role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		userId++;
	}

	// Generate getters and setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static int getUserId() {
		return userId;
	}

	public static void setUserId(int userId) {
		User.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// User Registration method
	public void registerNewUser() {
		System.out.println("Welcome to the expense tracker app");
		System.out.println("Register here: ");

		try {
			db.connect();
			// Get a valid username
			while (true) {
				// Prompt user for username
				System.out.print("Please enter your username: ");
				username = scanner.nextLine();
				if (username == null || username.trim().isEmpty()) {
					System.out.println("Error: Username cannot be empty");

				}
				if (!db.checkUserExists(username)) {
					//System.out.println("exist");
				} else {
					break;
				}

			}

			// Get a valid email
			while (true) {
				// Prompt user for email - future optimization, validate email address
				System.out.print("Please enter your email: ");
				email = scanner.nextLine();
				if (email == null || email.trim().isEmpty()) {
					System.out.println("Error: Email cannot be empty");

				} 
				if (!db.checkEmailExists(email)) {
					//System.out.println("exist");
				} else {
					break;
				}

			}

			// Get a valid password
			while (true) {
				// Prompt user for the password
				System.out.print("Please enter your password: ");
				password = scanner.nextLine().trim();
				if (password == null || password.trim().isEmpty()) {
					System.out.println("Error: Username cannot be empty");

				} else if (!validatePassword(password)) {
					System.out.println("Error: Password does not meet security requirements");

				} else {
					break;
				}
			}
			db.connect();
			boolean isUserAdded = db.addUser(username, email, hashPassword(password));

			if (isUserAdded) {
				System.out.println("Registration successful! Welcome, " + username);
			}
		} catch (Exception e) {
			System.out.println("Registration failed: " + e.getMessage());
		} finally {
			db.close();
		}

	}

	// Authentication methods
	public boolean validatePassword(String password) {
		String passwordPatternString = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!()_:;<>,.?/~`\\-]).{8,}$";
		if (!password.matches(passwordPatternString)) {
			System.out.println(
					"Password must be at least 8 letters long, include a number, uppercase, and a special character");
			return false;
		}
		return true;

	}

	// Method to hash password using BCrypt
	public String hashPassword(String password) {
		String hashedPasswordString = BCrypt.hashpw(password, BCrypt.gensalt(12));
		return hashedPasswordString;

	}

	// Utility methods

	// toString method
	public String toString() {
		return userId + ", " + email;
	}

	// Method to compare username
	@Override
	public boolean equals(Object obj) {
		// Check if the obj is an instance of User
		if (obj instanceof User) {
			User other = (User) obj;
			return this.username.equals(other.username);
		}
		return false;
	}

}
