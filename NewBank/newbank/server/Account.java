package newbank.server;

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
		return (accountName + ": " + openingBalance);
	}

}
