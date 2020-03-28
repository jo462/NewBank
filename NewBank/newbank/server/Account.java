package newbank.server;

import java.util.HashSet;
import java.util.Set;

public class Account {
	//account name include First name and Surname
	private String accountName;
	//openingBalance is the starting deposit of the customer
	private double openingBalance;
	//Email Address - this is to be used for username
	private String emailAddress;
	//Mail Address_ this to be used for sending letters and post to a customer
	private String mailAddress;
    private String password;



	//Constructor for a new account which is opened using account name and starting/ opening balance/email address/ and mail address
	public Account(String accountName, double openingBalance, String emailAddress,String mailAddress,String password) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.emailAddress= emailAddress;
		this.mailAddress= mailAddress;
		this.mailAddress= password;

	}
	
	//This method when called returns the account name and opening balance
	public String toString() {
		return (accountName + ": " + openingBalance + ": " + emailAddress + ": " + mailAddress ": " + password );
	}
    //Below accessor methods toGet customer information one by one
	//method toGetAccountName
	public String getAccountName(){
		return accountName;
	}
	//method toGetOpeningBalance
	public double getOpeningBalance(){
		return openingBalance;
	}
	//method GetEmailAddress
	public String getEmailAddress(){ return emailAddress;
	}
	//method GetEmailAddress
	public String getMailAddress(){
		return mailAddress;
	}
	//method GetPassword
	public String getPassword(){

		return password;
	}

	/*
	this method does not allow duplicate email addresses. if an existing email address is already in use,
	 a message box notify to the user to change his email address, else a message notify_your email has been added
	*/
	public void duplicatedEmailsNotAllowed(){
		Set<Account> accountSet = new HashSet<Account>();
		for(Account acc : accountSet){
			if(accountSet.contains(acc)){
				System.out.println("Account with account email " + acc.getEmailAddress() + "already exists. Insert a not existing email address");
			} else {
				accountSet.add(acc);
				System.out.println("Your email has been added");
			}
		}

	}



}
