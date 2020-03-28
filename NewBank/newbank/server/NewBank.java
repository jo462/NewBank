package newbank.server;

import java.util.HashMap;

public class NewBank {
	
	private static final NewBank bank = new NewBank();
	private HashMap<String,Customer> customers;
	
	private NewBank() {
		customers = new HashMap<>();
		addTestData();
	}
	
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
	
	public static NewBank getBank() {
		return bank;
	}
	
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			String[] array = request.split(" ");
			switch(array[0]) {
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			case "NEWACCOUNT" : 
				if(customers.get(customer.getKey()).hasAccount(array[1]))
					return "FAIL";
				customers.get(customer.getKey()).addAccount(new Account(array[1], 0.0));
				return "SUCCESS";
			case "PAY":
				if(customers.containsKey(array[1]) == false)
					return "FAIL";
				if(customers.get(customer.getKey()).pay(Double.parseDouble(array[2]))){
					customers.get(array[1]).acceptPayment(Double.parseDouble(array[2]));
					return "SUCCESS";
				}
			case "MOVE":
				if(customers.get(customer.getKey()).move(array[2], array[3], Double.parseDouble(array[1])))
					return "SUCCESS";
			default : return "FAIL";
			}
		}
		return "FAIL";
	}
	
	private String showMyAccounts(CustomerID customer) {
		return (customers.get(customer.getKey())).accountsToString();
	}

}
