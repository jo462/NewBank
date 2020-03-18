package newbank.server;

public class Account {
	//account name include First name and Surname
	private String accountName;
	//openingBalance is the starting deposit of the customer
	private double openingBalance;
	//Email Address - this is to be used for username
	private String emailAddress;
	//Mail Address_ this to be used for sending letters and post to a customer
	private String mailAddress;
	//Constructor for a new account which is opened using account name and starting/ opening balance
	public Account(String accountName, double openingBalance, String emailAddress,String mailAddress) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
		this.emailAddress= emailAddress;
		this.mailAddress= mailAddress;

	}
	
	//This method when called returns the account name and opening balance
	public String toString() {
		return (accountName + ": " + openingBalance + ": " + emailAddress + ": " + mailAddress);
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
	//method toGetEmailAddress
	public String getEmailAddress(){
		return emailAddress;
	}
	//method toGetEmailAddress
	public String getMailAddress(){
		return mailAddress;
	}



}
