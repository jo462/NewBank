package newbank.server;

import java.util.ArrayList;

public class Customer {
	
	private ArrayList<Account> accounts;
	private String emailAddress;
	public Customer(String emailAddress) {
		this.emailAddress = emailAddress;
		accounts = new ArrayList<>();
	}
	
	public String getEmailAdress(){
		return emailAddress;
	}

	public String accountsToString() {
		String s = "";
		for(Account a : accounts) {
			s += a.toString();
		}
		return s;
	}

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
	
	public void acceptPayment(double amount){
		accounts.get(0).add(amount);

	}

	public boolean deposit(String account,double amount){
		for(Account a: accounts){
			if(a.getAccountName().equals(account)){
				a.add(amount);
				return true;
			}
		}
		return false;
	}
	
	public boolean withdraw(String account,double amount){
		for(Account a: accounts){
			if(a.getAccountName().equals(account)){
				return a.deduct(amount);
			}
		}	
		return false;
	}
	
	public void addAccount(Account account) {
		accounts.add(account);		
	}
		
}
