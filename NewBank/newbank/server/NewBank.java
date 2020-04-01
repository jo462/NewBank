package newbank.server;
/*
*
* @author Samuel
*
*/
import java.util.HashMap;

public class NewBank {
	//Singelton class pattern
	private static final NewBank bank = new NewBank();
	//Hashmaps to store emailadresses, customers, login data
	private HashMap<String, String> emailadresses;
	private HashMap<String,Customer> customers;
	private HashMap<String,CustomerPassword> logInData;
	private NewBank() {
		emailadresses = new HashMap<>();
		customers = new HashMap<>();
		logInData = new HashMap<>();
		addTestData();
	}
	//Add Test data
	private void addTestData() {
		Customer bhagy = new Customer("bhagy@gmail.com");
		emailadresses.put("bhagy@gmail.com","Bhagy");
		bhagy.addAccount(new Account("Main", 1000.0));
		customers.put("Bhagy", bhagy);
		logInData.put("Bhagy",new CustomerPassword("Bhagy@1234"));
		Customer christina = new Customer("christina@gmail.com");
		emailadresses.put("christina@gmail.com","Christina");
		christina.addAccount(new Account("Savings", 1500.0));
		customers.put("Christina", christina);
		logInData.put("Christina",new CustomerPassword("Christina@1234"));
		Customer john = new Customer("john@gmail.com");
		emailadresses.put("john@gmail.com","John");
		john.addAccount(new Account("Checking", 250.0));
		customers.put("John", john);
		logInData.put("John",new CustomerPassword("John@1234"));
	}
	
	public static NewBank getBank() {
		return bank;
	}
	// Method which handles addition of new user
	public synchronized String addUser(String username, String emailAdress,  String password){
		//if username exist then cannot be added
		if(customers.containsKey(username))
			return "Username Already Exist!";
		//if email adress already in use cannot be aadded
		if(emailadresses.containsKey(emailAdress))
			return "Email Adress Already in Use!";
		// Add cutomer to bank
		Customer customer = new Customer(emailAdress);
		customer.addAccount(new Account("Main",0.0));
		emailadresses.put(emailAdress, username);
		customers.put(username, customer);
		logInData.put(username, new CustomerPassword(password));
		return "User Successfuly Added!";
	}
	//Method which handles authentication
	public synchronized CustomerID checkLogInDetails(String userName, String password) {
		if(customers.containsKey(userName)) {
			if(logInData.get(userName).verifyPassword(password))
				return new CustomerID(userName);
		}
		return null;
	}

	// commands from the NewBank customer are processed in this method
	public synchronized String processRequest(CustomerID customer, String request) {
		if(customers.containsKey(customer.getKey())) {
			String[] array = request.split(" ");
			switch(array[0]) {
			//Enables user to read his own profile
			case "SHOWMYPROFILE": return customer.getKey()+"\n"+customers.get(customer.getKey()).getEmailAdress()+"\n"+showMyAccounts(customer);
			//Enables user to change username
			case "CHANGEUSERNAME": 
				if(customers.containsKey(array[1]))
					return "FAIL";
				customers.put(array[1], customers.get(customer.getKey()));
				logInData.put(array[1], logInData.get(customer.getKey()));
				customers.remove(customer.getKey());
				logInData.remove(customer.getKey());
				customer.setKey(array[1]);
				return "SUCCESS";
			//Enables user to change email adress	
			case "CHANGEPASSWORD": 
				if(logInData.get(customer.getKey()).verifyPassword(array[1]))
					return "FAIL";
				logInData.put(customer.getKey(), new CustomerPassword(array[1]));
				return "SUCCESS";			
			//Enables user to get info about all accounts	
			case "SHOWMYACCOUNTS" : return showMyAccounts(customer);
			//Enables user to deposit 
			case "DEPOSIT":
				if(customers.get(customer.getKey()).deposit(array[1], (double)Double.parseDouble(array[2])))
					return "SUCCESS";
			//Enables user to withdraw	
			case "WITHDRAW":
				if(customers.get(customer.getKey()).withdraw(array[1], (double) Double.parseDouble(array[2])))
					return "SUCCESS";
			//Enables user to add account			
			case "NEWACCOUNT" : 
				if(customers.get(customer.getKey()).hasAccount(array[1]))
					return "FAIL";
				customers.get(customer.getKey()).addAccount(new Account(array[1], 0.0));
				return "SUCCESS";
			//Enables user to pay to other users
			case "PAY":
				if(customers.containsKey(array[1]) == false)
					return "FAIL";
				if(customers.get(customer.getKey()).pay((double)Double.parseDouble(array[2]))){
					customers.get(array[1]).acceptPayment((double)Double.parseDouble(array[2]));
					return "SUCCESS";
				}
			//Enables user to move amount from one account to other	
			case "MOVE":
				if(customers.get(customer.getKey()).move(array[2], array[3], (double) Double.parseDouble(array[1])))
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
