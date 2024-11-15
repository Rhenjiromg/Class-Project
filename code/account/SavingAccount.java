package account;

public class SavingAccount extends Account {
	private final double penalty = 5.0;
	private final int withdrawalLimit = 6;
	private double intrestRate;
	private int numberOfWithdrawals;
	
	SavingAccount() {
		numberOfWithdrawals = 0;
		setBalance(0.0);
		intrestRate = 0.1;
	}
	
	
	SavingAccount(double balance) {
		numberOfWithdrawals = 0;
		setBalance(balance);
		intrestRate = 0.1;
	}
	
	
	public void withdraw(double withdrawlAmount) {
		super.withdraw(withdrawlAmount);
		++numberOfWithdrawals;
		if (numberOfWithdrawals >= withdrawalLimit) {
			double b = getBalance();
			setBalance(b -= penalty); // minus $5
		}
	}
	
	public void deposit(double amount) {
		super.deposit(amount);
	}
	
	public void transferFunds(Account targetAccount) {
		super.transferFunds(targetAccount);
	}
	
	
	public void applyInrest() {
		String[] creationDate = super.getCreationDate().split(" ");
		String[] currentDate = super.getCurrentDate().split(" ");
		
		// if it's the same day and month of the next year
		// apply the interest to the balance
		if (creationDate[0].equals(currentDate[0]) 
				&& creationDate[1].equals(currentDate[1]) 
				&& creationDate[2].equals(String.valueOf(Integer.valueOf(currentDate[2]) + 1))) {
			double b = getBalance();
			setBalance(b * (1 + intrestRate));
		}
	}
	
	
	public double getIntrestRate() {
		return intrestRate;
	}
	
	
	public void setIntrestRate(double newIntrestRate) {
		this.intrestRate = newIntrestRate;
	}
}