package shared;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class SavingAccount extends Account {
	private final double penalty = 5.0;
	private final int withdrawalLimit = 6;
	private double intrestRate = 0.1; //this is hardcode for now
	private int numberOfWithdrawals = 0;

	SavingAccount(String ID, String bal, String date, String date2, String num) {
		super(ID, bal, date, date2);
		numberOfWithdrawals = Integer.valueOf(num);
	}

	private void withdrawLimiter(){
		if (numberOfWithdrawals >= withdrawalLimit) {
			if( balance >= penalty){
				balance -= penalty;
			}
			else{
				balance = 0.0;
			}
			numberOfWithdrawals = 0;
		}
	}
	
	
	public boolean withdraw(double withdrawlAmount) {
		boolean success = super.withdraw(withdrawlAmount);
		++numberOfWithdrawals;
		withdrawLimiter();
		return success;
	}
	
	public void deposit(double amount) {
		super.deposit(amount);
	}
	
	public boolean transferFunds(Account targetAccount, double bal) {
		boolean success = super.transferFunds(targetAccount, bal);
		++numberOfWithdrawals;
		withdrawLimiter();
		return success;
	}
	
	
	public void applyInrest() {
		LocalDate current = LocalDate.now();
		int yearDiff = Period.between(lastCheck, current).getYears();
		lastCheck = lastCheck.plusYears(yearDiff);
		//this way we can keep track of the last cycle of interest that we applied

		for (int i = yearDiff ; i > 0 ; i--) {
			balance = balance * (1 + intrestRate);
		}	
	}
	
	
	
	public double getIntrestRate() {
		return intrestRate;
	}

	public ArrayList<String> filePrep(){
		// Prepare the data for file storage 
		ArrayList<String> data = super.filePrep();
		data.add(String.valueOf(penalty)); 
		data.add(String.valueOf(numberOfWithdrawals)); 
		return data;
	}
	
	
	public void setIntrestRate(double newIntrestRate) {
		this.intrestRate = newIntrestRate;
	}
}