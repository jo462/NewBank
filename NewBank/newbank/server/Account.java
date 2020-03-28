package newbank.server;

public class Account {
	
	private String accountName;
	private double openingBalance;

	public Account(String accountName, double openingBalance) {
		this.accountName = accountName;
		this.openingBalance = openingBalance;
	}
	
	public String getAccountName(){
		return accountName;
	}

	public double getBalance(){
		return openingBalance;
	}

	public void add(double money){
		openingBalance += money;
	}

	public boolean deduct(double money){
		if(money > openingBalance)
			return false;
		openingBalance -= money;
		return true;
	}	

	public String toString() {
		return (accountName + ": " + openingBalance);
	}

}
