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

import newbank.client.ExampleClient;

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
		return String.format("%tY-%tm-%td   %-10s %-20s %-12s %,20.2f   %-10.10s\n", date,date,date, accountNo, accountName, transType,amount, description);
	}

		
	/*
	 * Use the below to test the classes and gain an understanding and experiment
	 */
	
	public static void main(String[] args)  {
		
		//Test Data1:
		Entry myEntry = new Entry(dateInput("03-03-2020"),"00000501","Main",TransType.TRANSFER,100000000.23,"Pete","Test desc");
		Entry myEntry2 = new Entry(dateInput("01-04-2020"),"00000001","Main",TransType.TRANSFER,-100000000.23,"John","Test desc2");
		
		
		ArrayList<Entry> myTrans = new ArrayList<Entry>();
		
		myTrans.add(myEntry);
		myTrans.add(myEntry2);
		
		//Test Data2:
		Entry myEntry3 = new Entry(dateInput("02-04-2020"),"00000501","Main",TransType.ACCOUNTOPEN,6000.23,"Pete","Test desc3");
		Entry myEntry4 = new Entry(dateInput("05-05-2020"),"00000501","Main",TransType.ACCOUNTOPEN,-6000.23,"Pete","Test desc");
				
				
		ArrayList<Entry> myTrans2 = new ArrayList<Entry>();
				
		myTrans2.add(myEntry3);
		myTrans2.add(myEntry4);
		
				
		Transaction test1 = new Transaction(myTrans);
		Transaction test2 = new Transaction(myTrans2);
		Ledger myLedger = new Ledger();
		myLedger.addTransaction(test1);
		myLedger.addTransaction(test2);
		
		System.out.println("Testing retrieval of a transaction number:");
		System.out.println(myLedger.getTransaction("0000000002").toString());
		
		System.out.println("Testing Account printing");
		myLedger.printAccountTransactions("00000501");
		
		System.out.println();
		System.out.println("Testing Account & Period printing");
		myLedger.printMonthlyAccountTransactions("00000501", 3, 2020);
		
		
		
	//	System.out.println(test1);
		
	}



}
