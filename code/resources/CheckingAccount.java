package resources;

public class CheckingAccount extends Account {
	private final double maintenanceFee = 5.0;
	
	
	public CheckingAccount() {
		setBalance(0);
	}
	
	public CheckingAccount(double balance) {
		this.setBalance(balance);
	}
	
	public void deposit(double amount) {
		super.deposit(amount);
	}
	
	public void withdraw(double amount) {
		super.withdraw(amount);
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
			setBalance(getBalance() - maintenanceFee);
		}
	}
}