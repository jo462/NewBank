package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	
	
	public Customer() {
		accounts = new ArrayList<>();
	}
	
	//Lists all customer accounts
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

	//Adds a new account for the customer
	public void addAccount(Account account) {
		accounts.add(account);		
	}
}
