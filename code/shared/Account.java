package shared;

import java.time.LocalDate;
import java.util.ArrayList;

//represented in file as {accID + " " + balance + " " + creationDate}
public class Account {

	protected String accountID;
	protected static int count = 0; //right now this is instance based... might want to get some sort of reference to keep in between?
	protected double balance;
	protected LocalDate creationDate;
	protected LocalDate lastCheck;
	private boolean updateCount = true;
	
	// Account constructor, for create new account
	public Account() {
		/*
    	while (count == 0) { //condition on new server that doesnt know of pre-created files
			//TODO: logic to synch count with databbase here
		}
		*/
		accountID = Integer.toString(++count);

		creationDate = LocalDate.now();
		setBalance(0);
		this.lastCheck = creationDate;
	}

	// Acc constructor for creat acc from file
	public Account(String ID, String bal, String date, String date2) {
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

	public boolean transferFunds(Account targetAccount, double amount) {
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