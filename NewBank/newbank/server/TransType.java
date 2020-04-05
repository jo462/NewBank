package newbank.server;
/*
 * Author @Masi_Majange
 */
public enum TransType {

	ACCOUNTOPEN (0), //Used for account openings
	DEPOSIT (1), //Used when making a deposit
	WITHDRAW (2), //Used when a withdrawal is being made
	TRANSFER (3),	//Used when a transfer is being made between accounts
	CHARGES (4),	//Used for bank charges
	ODINTEREST (5), //Used for charging interest in overdrafts
	LOAN (6), //Used for when there is an increase in loan (new loan or increase of loan principal)
	LOANINTEREST (7), //Used for loan interest
	REPAYMENT (8); //Used for when a loan is being repaid, partially or in full

	private final int transCode;

	TransType(int transCode) {
		
	this.transCode = transCode;
	}
	
	public int getTranscode() {
		return this.transCode;
	}
	
}
