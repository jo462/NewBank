package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String emailAddress;
	
	//Constructor for a new customer email address
	public Customer(String emailAddress) {
		this.emailAddress = emailAddress;
		accounts = new ArrayList<>();
	}
	//Accessor method to get customer email address
	public String getEmailAdress(){
		return emailAddress;
	}
	//Method to show customer accounts
	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}
	//Method to check if customer has an existing account
	public boolean hasAccount(String name){
		for(Account a : accounts)
			if(a.getAccountName().equals(name))
				return true;
		return false;	
	}
	
	public boolean pay(double amount){
		for(Account a: accounts)
			if(a.getBalance() > amount)
				return a.deduct(amount);
		return false;	
	}
	//Method to allow customer to transfer money between two accounts
	public boolean move(String from, String to, double amount){
		if(from.equals(to))
			return true;
		for(Account a: accounts){
			if(a.getAccountName().equals(from)){
				for(Account b: accounts)
					if(b.getAccountName().equals(to)){
						if(a.deduct(amount)){
							b.add(amount);
							return true;
						}
						return false;
					}
			}

		}
		return false;
	}
	//Method to allow customer to accept payment into bank account
	public void acceptPayment(double amount){
		accounts.get(0).add(amount);

	}
	//Method to allow customer to deposit money into bank account
	public boolean deposit(String account,double amount){
		for(Account a: accounts){
			if(a.getAccountName().equals(account)){
				a.add(amount);
				return true;
			}
		}
		return false;
	}
	//Method to allow customer to withdraw money from bank account
	public boolean withdraw(String account,double amount){
		for(Account a: accounts){
			if(a.getAccountName().equals(account)){
				return a.deduct(amount);
			}
		}	
		return false;
	}
	//Method to allow customer to create a new bank account
	public void addAccount(Account account) {
		accounts.add(account);		
	}
	
	//Main account is always the first account added
	public Account getMainAccount() {
		return accounts.get(0);
	}
	
	//Returns the Customer ID
	public String getCustomerID() {
		return emailAddress;
	}
	
	//Method to get account number from a given account name
		public String getAccountNo(String accountName){
			for(Account a: accounts){
				if(a.getAccountName().equals(accountName)){
					return a.getAccountNumber();
				}
			}
			return null;
		}
	
	//Method to get all customer accounts
	public ArrayList<Account> getAllAccounts(){
		return accounts;
	}
		
		
}
