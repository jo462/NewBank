package newbank.server;

import java.util.HashSet;
import java.util.Set;

public class Account {
	
	private String accountName;
	private static int accCount=1;
	private double openingBalance;
	private String accountNumber;


	//Constructor for a new account which is opened using account name and starting/ opening balance
	public Account(String accountName, double openingBalance) {
		//accCount++;
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		accountNumber = String.format("%08d", accCount++);
	}
	
	//This method when called returns the account name and opening balance
	public String toString() {
		return String.format("%s %s %.2f",accountNumber, accountName, accountNumber) + "\n" ;
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

	//Method to get account number
	public String getAccountNumber() {
		return accountNumber;
	}

}
