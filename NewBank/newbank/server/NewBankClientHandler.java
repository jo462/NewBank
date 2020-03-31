package newbank.server;
/*
*
* @author Samuel
*
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NewBankClientHandler extends Thread{
	
	private NewBank bank;
	private BufferedReader in;
	private PrintWriter out;
	
	
	public NewBankClientHandler(Socket s) throws IOException {
		bank = NewBank.getBank();
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		out = new PrintWriter(s.getOutputStream(), true);
	}
	//Method that handles login functionality
	public CustomerID login() throws IOException{
		//User will get 3 chances
		int count = 3;
		while(count-->0){
			out.println("Enter details for login");
			out.println("Enter Username");
			// ask for user name
			String userName = in.readLine();
			// ask for password
			out.println("Enter Password");
			String password = in.readLine();
			out.println("Checking Details...");
			// authenticate user and get customer ID token from bank for use in subsequent requests
			CustomerID customer = bank.checkLogInDetails(userName, password);
			// if the user is authenticated then get requests from the user and process them 
			if(customer != null)
				return customer;
			out.println("Invalid Username or Password");
		}
		return null;
	}

	
		
	public void run() {
		// keep getting requests from the client and processing them
		try {
			//Salutation
			out.println("Welcome to NewBank");
			String entry = "";
			do{
				//On boarding
				out.println("Enter 1 to Login or 2 for creating new user account");
				entry = in.readLine();
				//Invalid entry ask user to enter again
				if(entry.equals("1") || entry.equals("2"))
					break;
				out.println("Invalid entry");
			}while(true);
			CustomerID customer = null;
			//call login if entry is 1
			if(entry.equals("1"))
				customer = login();
			//Signup new user if entry 2
			else{
				while(true){
					// ask for user name	
					out.println("Enter Username");
					String userName = in.readLine();
					// ask for user name	
					out.println("Enter Email Adress");
					String emailAdress = in.readLine();
					// ask for password
					out.println("Enter Password");
					String password = in.readLine();
					// adding new user
					String res = bank.addUser(userName,emailAdress, password);
					out.println(res);
					if(res.equals("User Successfuly Added!"))
						customer = new CustomerID(userName);
					if(customer != null)
						break;
					
				}
			}
			// If customer exist he can make request
			if(customer != null) {
				out.println("Log In Successful. What do you want to do?");
				while(true) {
					String request = in.readLine();
					System.out.println("Request from " + customer.getKey());
					String responce = bank.processRequest(customer, request);
					out.println(responce);
				}
			}
			else{
				out.println("Login Failed..Exiting");
				return;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

}
