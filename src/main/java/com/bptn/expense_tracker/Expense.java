package com.bptn.expense_tracker;


import java.time.LocalDateTime;

public class Expense {
	
	//Declare variables
	private int expenseId;
	private int userId;
	private double amount;
	private String category;
	private String description;
	private LocalDateTime date;
	
	
	//Constructors
	
	public Expense() {

    }
	
	public Expense(int userId, double amount, String category, String description, LocalDateTime date) {
		this.userId = userId;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.date = date;
	}
	
	
	//Getters and setters
	public int getExpenseId() {
		return expenseId;
	}


	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	//Helper methods
	//toString method
	public String toString() {
		return "Expense {" + "id = " + expenseId + "userId = " + userId +
				"amount = " + amount +  "category = " + category +
				"description = " + description + "date = " + date;
	}
	
//	public boolean isValidAmount() {
//		
//	}
//	
	
	
	
	
	
}
