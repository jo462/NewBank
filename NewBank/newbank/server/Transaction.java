package newbank.server;
import java.time.LocalDate;
/*
 * Author @Masi_Majange
 */
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * A transaction is a series of linked entries.
 */
public class Transaction {

	private ArrayList<Entry> transaction = new ArrayList<Entry>();
		
	/*
	 * A transaction is built from a number of entries. You'll need to build up the
	 * transaction list at source as an ArrayList<Entry> then you declare as type
	 * Transaction
	 */
	public Transaction (ArrayList<Entry> entries) {

		this.transaction = entries;
	}

	/*
	 * Returns the entries in a transaction
	 */
	public String toString() {
		String myTransactions ="" ;
		for(Entry t : transaction) {
			myTransactions = myTransactions + t.toString();
		}
		return myTransactions;	

	}


	/*
	 * This method prints transactions linked to an account number at an <Entry> level.
	 * It can be called from the ledger as a sub method while it iterates over multiple
	 * transactions
	 */
	public void hasAccountNo(String accountNo) {
		transaction
		.stream()
		.filter(x -> x.getAccountNo().contains(accountNo))
		.forEach(System.out::print);
	}
	
	/*
	 * This method prints transactions linked to an account number and period (month and year)
	 * at an <Entry> level
	 */
	
	public void forPeriod(String accountNo, Integer month, Integer year) {
		
		Predicate<Entry> isPeriod = e-> ((month.equals(e.getDate().getMonth().getValue()) && (year.equals(e.getDate().getYear()))));
		Predicate<Entry> isAccount = e-> e.getAccountNo().equals(accountNo);
		
		transaction
		.stream()
		.filter(isAccount.and(isPeriod))
		.forEach(System.out::print);
				
	}




}
