package newbank.server;

import java.util.HashSet;
import java.util.Set;

public class Account {
	
	private String accountName;
	private double openingBalance;



	//Constructor for a new account which is opened using account name and starting/ opening balance
	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	//This method when called returns the account name and opening balance
	public String toString() {
		return String.format("%s %.2f", accountName, openingBalance) + "\n" ;
	}
    //Below accessor methods toGet customer information one by one
	//method toGetAccountName
	public String getAccountName(){
		return accountName;
	}

    //Below accessor methods toGet customer information one by one
	//method toGetBalance
	public double getBalance(){
		return openingBalance;
	}
	//Method to add money to account
	public void add(double money){
		openingBalance += money;
	}
	
	//Method to deduct money from account
	public boolean deduct(double money){
		if(money > openingBalance)
			return false;
		openingBalance -= money;
		return true;
	}


}
