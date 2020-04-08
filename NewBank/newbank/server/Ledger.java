package newbank.server;

import java.time.LocalDate;
/*
 * Author @Masi_Majange
 */
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Ledger {

	private static HashMap<String,Transaction> myLedger = new LinkedHashMap<String,Transaction>();

	private String transactionNo;
	private int transNo=1;

	public void addTransaction(Transaction transaction) {

		transactionNo = String.format("%010d", transNo++);
		myLedger.put(transactionNo, transaction);


		System.out.println("Transaction added: "+ transactionNo);
		printHeader();
		System.out.println(transaction.toString());
	}

	/*
	 * Returns entries related to a given transaction number
	 */
	public Transaction getTransaction(String transactionNo) {

		return myLedger.get(transactionNo);
	}


	/*
	 * Print ALL transactions in the ledger from all accounts and all periods
	 */
	public void printAllTransactions() {
		myLedger
		.keySet()
		.stream()
		.forEach(x -> System.out.println(x+" \n"+ myLedger.get(x)));

	}

	/*
	 * Outputs transactions linked to an account number with no period constraint
	 *
	 */
	public void printAccountTransactions(String accountNo) {
		printHeader();
		myLedger
		.values()
		.stream()
		.forEach(x -> x.hasAccountNo(accountNo));
	}


	/*
	 * Outputs transactions specific to an account number and period (month, year)
	 */
	public void printMonthlyAccountTransactions(String accountNo, Integer month, Integer year) {

		printHeader();
		myLedger
		.values()
		.stream()
		.forEach(x -> x.forPeriod(accountNo, month, year));

	}

	/*
	 * Outputs transactions specific to an account number and period (month, year)
	 */
	public ArrayList<Entry> getMonthlyAccountTransactions(String accountNo, Integer month, Integer year) {

		printHeader();
		List<Entry> myList =		myLedger
				.values()
				.stream()
				.map(x -> x.forPeriod(accountNo, month, year))
				.filter(x -> x!=null)
				.collect(Collectors.toCollection(ArrayList<Entry>::new));
		return (ArrayList<Entry>) myList;
	}


	/*
	 * Returns the opening balance for a given account and period
	 */
	public Double openingBalance(String accountNo, Integer month, Integer year) {

		Double balance = 0.00;

		LocalDate myDate = LocalDate.of(year, month, 1);

		balance = 	myLedger
				.values()
				.stream()
				.filter(x -> x.getTransactionDate().isBefore(myDate))
				.mapToDouble(x -> x.getAmount(accountNo))
				.sum();

		return balance;
	}

	/*
	 * Returns the closing balance for a given account and period
	 */

	public Double closingBalance(String accountNo, Integer month, Integer year) {

		Double balance = 0.00;

		//Resetting for the new year
		if(month==12) {
			month=0;
			year++;
		}

		LocalDate myDate = LocalDate.of(year, month+1, 1);

		balance = 	myLedger
				.values()
				.stream()
				.filter(x -> x.getTransactionDate().isBefore(myDate))
				.mapToDouble(x -> x.getAmount(accountNo))
				.sum();

		return balance;
	}


	/*
	 * Returns the current balance for a given account
	 */
	public Double currentBalance(String accountNo) {

		Double balance = 0.00;

		balance = 	myLedger
				.values()
				.stream()
				.mapToDouble(x -> x.getAmount(accountNo))
				.sum();

		return balance;
	}



	/*
	 * Prints a header. Can be called before output of transactions
	 */
	private void printHeader() {
		String h1 = "Date";
		String h2 = "Acc No";
		String h3 = "Acc Name";
		String h4 = "Trans. Type";
		String h5 = "Amount";
		String h6 = "Customer ref.";
		System.out.println(String.format("%-12s %-10s %-20s %-12s %20s   %-15.15s", h1,h2,h3,h4,h5,h6));
	}




}
