package com.bptn.expense_tracker;

import com.bptn.expense_tracker.exceptions.EmptyFieldException;

public class TestApp {

	public static void main(String[] args) throws EmptyFieldException {
		DatabaseHandler db = new DatabaseHandler();
//		db.connect();
//		db.createTable();
//		db.close();
		User app = new User();
		app.registerNewUser();
				
		

	}

}
