package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	private int attempts = 3;
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
	//Testing new customer setup. The structure appears like "account name" - whatever you'd like, but for Customer it needs to record the actual name
	private void addTestData() {
		Customer bhagy = new Customer();
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		
		Customer christina = new Customer();
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		
		Customer john = new Customer();
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
	}
	
	//Returns name of bank, or should it be branch?
	public static NewBank getBank() {
		return bank;
	}
	
	//Checks if a customer ID exists, if so it returns the username otherwise the null value return
	/*
	 commands from the NewBank customer are processed in this method
	In CustomerID.java add ArrayList<Customer> customers
	 to add and getKey
	 Also we need a method to generate userName from the credentials ex. accountName Luke Skywalker username walker1
	*/
	public synchronized CustomerID checkLogInDetails(String userName, String password) {

       int i;
		//while loop to count attempts
       while (attempts != 0) {
			if (customers.containsKey(userName) && customers.containsKey(password)) {
				CustomerID customerID = new CustomerID(userName);
				return customerID;}
			 else {
				i = attempts--;
				System.out.println("Incorrect, You have" + i + "attempts left");
			} if (i == 0){
				System.out.println("Maximum number of attempts exceeded");
			}
		}
	/*
	 commands from the NewBank customer are processed in this method
	In CustomerID.java add ArrayList<Customer> customers
	 to add and getKey
	*/
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			switch(request) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	
	private String showMyAccounts(CustomerID customer) {

		return (customers.get(customer.getKey())).accountsToString();
	}

	private void


}
