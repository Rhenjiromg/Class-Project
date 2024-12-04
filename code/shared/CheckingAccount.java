package shared;

//while bal dip below 100, -5. fee is charge on operation while this is true
public class CheckingAccount extends Account {
	private final double Fee = 5.0;
	
	public CheckingAccount() {
		super();
		accountID = "A1" + accountID;
	}
	
	public CheckingAccount(String accountID, String balance, String creationDate, String lastDate) {
		super(accountID, balance, creationDate, lastDate);
	}

	public void deposit(double amount) {
		super.deposit(amount);
		applyFee();
	}
	
	public boolean withdraw(double amount) {
		boolean success = super.withdraw(amount);
		applyFee();
		return success;
	}
	
	public boolean transferFunds(Account targetAccount, double bal) {
		boolean success = super.transferFunds(targetAccount, bal);
		applyFee();
		return success;
	}
	
	public void applyFee() {
		if (this.balance < 100 && this.balance > 5){
			this.balance -=  Fee;
		}
	}

	//this will just inherit the account fileprep as fee is hardcoded
}