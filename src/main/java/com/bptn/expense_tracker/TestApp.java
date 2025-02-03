package com.bptn.expense_tracker;

public class TestApp {

	public static void main(String[] args) {
		DatabaseHandler db = new DatabaseHandler();
		db.connect();
		db.createTable();
		db.close();

	}

}
