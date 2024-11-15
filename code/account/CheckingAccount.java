package account;

public class CheckingAccount extends Account {
	private double balance;
	private final double maintenanceFee = 5.0;
	
	
	public CheckingAccount() {
		balance = 0;
	}
	
	public CheckingAccount(double balance) {
		this.balance = balance;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void deposit(double amount) {
		super.deposit(amount);
	}
	
	public void withdraw(double amount) {
		super.withdraw(amount);
	}
	
	public void closeAccount() {
		super.closeAccount();
	}
	
	public void transferFunds(Account targetAccount) {
		super.transferFunds(targetAccount);
	}
	
	public void applyMaintenanceFee() {
		String[] creationDate = super.getCreationDate().split(" ");
		String[] currentDate = super.getCurrentDate().split(" ");
		
		// if the same date hits Ex: Nov 14, Dec 14
		// deduct the maintenanceFee of $5
		if (creationDate[1].equals(currentDate[1])) {
			balance -= maintenanceFee;
		}
	}
}