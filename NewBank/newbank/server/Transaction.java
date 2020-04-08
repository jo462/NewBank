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
	
	public Entry forPeriod(String accountNo, Integer month, Integer year) {
		
	//	Predicate<Entry> isPeriod = e-> ((month.equals(e.getDate().getMonth().getValue()) && (year.equals(e.getDate().getYear()))));
		//Predicate<Entry> isAccount = e-> e.getAccountNo().equals(accountNo);
			
		for(Entry e : transaction) {
			if(e.getAccountNo().equals(accountNo)) {
				if(e.getDate().getYear()==year&& e.getDate().getMonthValue()==month) {
					return e;
				}
			}
		}
	
		return 	null;
					
	}

	public LocalDate getTransactionDate() {
		return transaction.get(0).getDate();
		}
	
		
	/*
	 * Helper method sums up the total amounts relating to an account per transaction
	 */
	public Double getAmount (String accountNo) {
		
		Double total = 0.00;
		
		for(Entry e : transaction) {
			if(e.getAccountNo().equals(accountNo)) {
				total += e.getAmount();
			}
		}
		
		return total;
	}
	
	
}
