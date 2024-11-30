package shared;

import java.time.LocalDate;
import java.util.ArrayList;

//represented in file as {accID + " " + balance + " " + creationDate}
public class Account {

	protected final String accountID;
	protected static int count = 0;
	protected double balance;
	protected LocalDate creationDate;
	protected LocalDate lastCheck;

	// read use cases SRS
	// if you think there's a logical issue
	// be creative, solve it and tell us
	// on discord, or call out the issue
	// if it's too big of a change.

	// Account constructor, for create new account
	Account() {
		accountID = Integer.toString(++count);

		creationDate = LocalDate.now();
		setBalance(0);
		this.lastCheck = creationDate;
	}

	// Acc constructor for creat acc from file
	Account(String ID, String bal, String date, String date2) {
		this.accountID = ID;
		this.balance = Double.valueOf(bal);
		this.creationDate = LocalDate.parse(date);
		this.lastCheck = LocalDate.parse(date2);

	}

	public void deposit(double amount) {
		// log
		this.balance += amount;

	}

	public boolean withdraw(double amount) {
		// log
		if (!overdraft(amount)) {
			this.balance -= amount;
			return true;
		} else {
			return false;
		}

	}

	protected boolean transferFunds(Account targetAccount, double amount) {
		if (!overdraft(amount)) {
			this.balance -= amount;
			targetAccount.balance += amount;
			return true;
		} else {
			return false;
		}
	}

	protected boolean overdraft(double amount) {
		if (amount > this.balance) {
			return true;
		}
		return false;
	}

	public String getAccountID() {
		return accountID;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public ArrayList<String> filePrep() {
		// Prepare the data for file storage
		ArrayList<String> data = new ArrayList<>();
		data.add(accountID);
		data.add(String.valueOf(balance));
		data.add(creationDate.toString());
		data.add(lastCheck.toString());
		return data;
	}
}