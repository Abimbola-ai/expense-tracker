package com.bptn.expense_tracker;

import java.util.Scanner;

public class User {
    // Declare variables
    private String username;
    private String passwordHash;
    static int userId = 100 ;
    private String email;
    private String role;
    public static Scanner scanner = new Scanner(System.in);

    //Default constructor
    public User() {

    }

	//Parameterized Constructor
    public User(String username, String passwordHash, String email, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        userId++;
    }
    
    //Generate getters and setters
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
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
	

	//User Registration method
	public boolean registerNewUser(String username, String email, String password) {
		System.out.println("Welcome to Expense Tracker Registration page");
		System.out.print("Please enter your username: ");
		username = scanner.nextLine();
		System.out.print("Please enter your email address: ");
		email = scanner.nextLine();
		System.out.print("Please enter your password: ");
		password = scanner.nextLine();
		return false;
	}
	
	//Authentication methods
	public boolean validatePassword(String password) {
		return true;
		
	}
	
	public String hashPassword(String password) {
		return password;
		
	}
	
	//Utility methods
	
	//toString method
	public String toString() {
		return userId + ", " + email;
	}
	
	//Method to compare username
	@Override
	public boolean equals(Object obj) {
		//Check if the obj is an instance of User
		if (obj instanceof User) {
			User other = (User) obj;
			return this.username.equals(other.username);
		}
		return false;
	}
	

	
	
	

}
