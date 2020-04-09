package newbank.server;
import java.util.ArrayList;
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
	private Ledger bankLedger = new Ledger();
	private String newBankAcc = "00000000";
	private String newBankAccName = "New Bank PLC";

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
			switch(array[0].toUpperCase()) {
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
				return deposit(customer,array[1],(double)Double.parseDouble(array[2]),array[3]);
				//Enables user to withdraw	
			case "WITHDRAW":
				return withdraw(customer,array[1],(double)Double.parseDouble(array[2]),array[3]);
				//Enables user to add account			
			case "NEWACCOUNT" : 
				if(customers.get(customer.getKey()).hasAccount(array[1]))
					return "FAIL";
				customers.get(customer.getKey()).addAccount(new Account(array[1], 0.0));
				return "SUCCESS";
				//Enables user to pay to other users
			case "PAY":
				if(customers.containsKey(array[1]) == false) {
					return "PAYEE NOT FOUND, TRY AGAIN";}
					return pay(customer,array[1],(double) Double.parseDouble(array[2]),array[3]);
				
				//Enables user to move amount from one account to other	
			case "MOVE":
				return move(customer, array[2],array[3],(double) Double.parseDouble(array[1]),array[4]);
				//Enables user to get a statement
			case "STATEMENT":
				
				return statement(customer,array[1], Integer.parseInt(array[2]),Integer.parseInt(array[3]));
			
			
			default : return "FAIL";
			}
		}
		return "FAIL";
	}

	private String statement(CustomerID customer, String account, Integer month, Integer year) {
		
	String accountNo = customers.get(customer.getKey()).getAccountNo(account);
		
	ArrayList<Entry> monthlyTrans = bankLedger.getMonthlyAccountTransactions(accountNo, month, year);
	
	String myTransactions = monthlyTrans.toString().replace("[", "").replace(", ", "").replace("]", "").trim();
	String h1 = "Date";
	String h2 = "Acc No";
	String h3 = "Acc Name";
	String h4 = "Trans. Type";
	String h5 = "Amount";
	String h6 = "Customer ref.";
	String header1 = String.format("%-12s %-10s %-20s %-12s %20s   %-15.15s\n", h1,h2,h3,h4,h5,h6);
	String openingBalance = String.format("Opening balance: %,61.2f\n", bankLedger.openingBalance(accountNo, month, year));
	String closingBalance = String.format("Closing balance: %,61.2f\n", bankLedger.closingBalance(accountNo, month, year));
	String lines = "-----------------------------------------------------------------------------------------------------\n";
	
	 return lines+header1+lines + openingBalance + myTransactions+"\n"+lines+closingBalance+lines;
	}

	
	
	
	
	
	//Method to show the customer their bank balance
	private String showMyAccounts(CustomerID customer) {
				
		String h1 = "Acc No";
		String h2 = "Acc Name";
		String h3 = "Balance";
		String lines = "-----------------------------------------------------";
		String header = String.format("%-10s %-20s %20s",h1,h2,h3);
		
		String myAccounts = "";
				
		for(Account a : customers.get(customer.getKey()).getAllAccounts()) {
			myAccounts += String.format("%-10s %-20s %,20.2f\n", a.getAccountNumber(),a.getAccountName(), bankLedger.currentBalance(a.getAccountNumber()));
		}
				 
	return lines+"\n"+header+"\n"+lines+"\n"+myAccounts+"\n"+lines;
	}



	//Converts input to an absolute value
	private Double abs(Double amount) {

		if(amount<0) {
			amount = amount*-1;
		}

		return amount;
	}

	
	/*
	 * Customer payment using the system date
	 */
	private String pay(CustomerID customer,String payee, Double amount, String description ) {

		String payer = customers.get(customer.getKey()).getMainAccount().getAccountNumber();
		String payto = customers.get(payee).getMainAccount().getAccountNumber();

		if(bankLedger.currentBalance(payer)<amount) {
			return "Insufficient funds";
		} else {

			//Building the double entry
			Entry entry1 = new Entry(payto,customers.get(payee).getMainAccount().getAccountName(),TransType.TRANSFER,abs(amount),payee,description);
			Entry entry2 = new Entry(payer,customers.get(customer.getKey()).getMainAccount().getAccountName(),TransType.TRANSFER,abs(amount)*-1, customers.get(customer.getKey()).getCustomerID(), description);
			ArrayList<Entry> myPosting = new ArrayList<Entry>();

			myPosting.add(entry1);
			myPosting.add(entry2);

			postTransaction(new Transaction(myPosting));

			return "SUCCESS! New balance: "+bankLedger.currentBalance(payer);}

	}
		
	
	/*
	 * Customer move using the system date
	 */
	private String move(CustomerID customer,String from,String to, Double amount, String description ) {

		String accfrom = customers.get(customer.getKey()).getAccountNo(from);
		String accto = customers.get(customer.getKey()).getAccountNo(to);

		if(bankLedger.currentBalance(accfrom)<amount) {
			return "Insufficient funds";
		}
			
		if(accfrom==null || accto==null) {
			return "Invalid account details entered, try again";
		} else {

			//Building the double entry
			Entry entry1 = new Entry(accto,to,TransType.TRANSFER,abs(amount),customers.get(customer.getKey()).getCustomerID(),description);
			Entry entry2 = new Entry(accfrom,from,TransType.TRANSFER,abs(amount)*-1, customers.get(customer.getKey()).getCustomerID(), description);
			ArrayList<Entry> myPosting = new ArrayList<Entry>();

			myPosting.add(entry1);
			myPosting.add(entry2);

			postTransaction(new Transaction(myPosting));

			return "SUCCESS! New balance: "+bankLedger.currentBalance(accfrom);}

	}


	/*
	 * Customer deposit using the system date
	 */
	private String deposit(CustomerID customer,String accountName, Double amount, String description ) {

		String accountNo = customers.get(customer.getKey()).getAccountNo(accountName);

		if(accountNo==null) {
			return "Account name '"+accountName+ "' does not exist, try again";
		} else {

			//Building the double entry
			Entry entry1 = new Entry(accountNo,accountName,TransType.DEPOSIT,abs(amount),customers.get(customer.getKey()).getCustomerID(),description);
			Entry entry2 = new Entry(newBankAcc,newBankAccName,TransType.DEPOSIT,abs(amount)*-1, newBankAccName, "Customer Deposit");
			ArrayList<Entry> myPosting = new ArrayList<Entry>();

			myPosting.add(entry1);
			myPosting.add(entry2);

			postTransaction(new Transaction(myPosting));

			return "SUCCESS! New balance: "+ bankLedger.currentBalance(accountNo);}

	}

	/*
	 * Customer withdrawal using the system date
	 */
	private String withdraw(CustomerID customer,String accountName, Double amount, String description ) {

		String accountNo = customers.get(customer.getKey()).getAccountNo(accountName);

		if(accountNo==null) {
			return "Account name '"+accountName+ "' does not exist, try again";
		} else {

			//Building the double entry
			Entry entry1 = new Entry(accountNo,accountName,TransType.WITHDRAW,abs(amount)*-1,customers.get(customer.getKey()).getCustomerID(),description);
			Entry entry2 = new Entry(newBankAcc,newBankAccName,TransType.WITHDRAW,abs(amount), newBankAccName, "Customer Deposit");
			ArrayList<Entry> myPosting = new ArrayList<Entry>();

			myPosting.add(entry1);
			myPosting.add(entry2);

			postTransaction(new Transaction(myPosting));

			return "SUCCESS! New balance: "+bankLedger.currentBalance(accountNo);}

	}




	/*
	 * Adds a given transaction to the ledger
	 */
	private void postTransaction(Transaction transaction) {
		bankLedger.addTransaction(transaction);
	}



















}
