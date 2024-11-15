package account;

import resources.Time;

public class Account {
	
	private final String accountID;
	private static int count;
	private double balance;
	private Time time; // custom time class check Time.java
	private Boolean isActive;
	
	// read use cases SRS
	// if you think there's a logical issue
	// be creative, solve it and tell us
	// on discord, or call out the issue
	// if it's too big of a change.
	
	
	// Account constructor, 
	Account() {
		accountID = Integer.toString(++count);
	}
	
	
	// implement this function
//	Account(/*secondary constructor with arguments*/) {
//		// 
//	}
	
	
	protected void deposit(double amount) {
		// update the balance to amount
	}
	
	
	protected void withdraw(double amount) {
		// subtract from balance
	}
	
	
	protected void closeAccount() {
		// change account state
		// for later: probably delete .txt
	}
	
	
	protected void transferFunds(Account targetAccount) {
		//
	}
	
	
	public String getAccountID() {
		return accountID;
	}
	
	
	public double getBalance() {
		return balance;
	}
	
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	
	public String getCreationDate() {
		return time.getCreationDate();
	}
	
	
	public String getCurrentTime() {
		return time.getCurrentTime();
	}
}


























