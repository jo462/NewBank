package newbank.server;
/*
 * Author @Masi_Majange
 */
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

//import newbank.client.ExampleClient;

/*
 * An entry is a constituent of a transaction. E.g. when doing a transfer
 * between accounts. It consists of 2 concurrent entries, the first being
 * money moving from one account and the second being money moving into 
 * the receiving account
 */
public class Entry {
	
	private LocalDate date;
	private String accountNo;
	private String accountName;
	private TransType transType;
	private Double amount;
	private String user;
	private String description;
	
	/*
	 * Constructor for a transaction which specifies the date
	 */
	public Entry(LocalDate date, String accountNo, String accountName, TransType transactionType, Double amount, String user, String description) {
		this.date = date;
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.transType = transactionType;
		this.amount = amount;
		this.user = user;
		this.description = description;
	}
	
	
	/*
	 * Constructor for a transaction which uses system date
	 */
	public Entry(String accountNo, String accountName, TransType transactionType, Double amount, String user, String description) {
		this.date = LocalDate.now();
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.transType = transactionType;
		this.amount = amount;
		this.user = user;
		this.description = description;
	}
	
	/*
	 * Getter method for account number
	 */
	public String getAccountNo ()  {
		return accountNo;
	}
	
	/*
	 * Getter method for transaction type
	 */
	public TransType getTransactionType() {
		return transType;
	}
	
	/*
	 * Getter method for User
	 */
	public String getUser() {
		return user;
	}
	
	/*
	 * Getter method for Account name
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/*
	 * Getter method for date
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/*
	 * Getter method for amount
	 */
	public Double getAmount() {
		return amount;
	}
	
	private static LocalDate dateInput(String userInput) {
		
		DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate myDate = LocalDate.parse(userInput, dateformat);
		
		return myDate;
	}
	
	
	/*
	 * Printing a single entry
	 */
	public String toString() {
		String h1 = "Date";
		String h2 = "Acc No";
		String h3 = "Acc Name";
		String h4 = "Trans. Type";
		String h5 = "Amount";
			//	System.out.println(String.format("%-12s %-10s %-20s %-12s %20s", h1,h2,h3,h4,h5));
		return String.format("%tY-%tm-%td   %-10s %-20s %-12s %,20.2f   %-20.20s\n", date,date,date, accountNo, accountName, transType,amount, description);
	}
		
		
	}




