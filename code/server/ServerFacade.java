package server;

import java.util.ArrayList;

import resources.Account;
import resources.CheckingAccount;
import resources.FileIO;
import resources.Message;
import resources.MessageType;
import resources.SavingAccount;
import resources.User;

public class ServerFacade {
	FileIO fileIO = new FileIO();
	
	public Boolean processCredentials(String accountID) {
		User u = (User) fileIO.readOperator(accountID + ".txt");
		return u.getAccList().contains(accountID);
	}
	
	public Message depositAmount(String accountID, String amount) {
		Account a;

		if ('0' == accountID.charAt(1)) { // savings
			a = (SavingAccount) fileIO.readAccount(accountID + ".txt");
		} else {
			a = (CheckingAccount) fileIO.readAccount(accountID + ".txt");
		}

		a.deposit(Double.parseDouble(amount));
		fileIO.writeAccount(accountID, a);
		
		String result = "";
		ArrayList<String> arr = a.filePrep();
		
		for (int x = 0; x < arr.size(); x++) {
			result += arr.get(x);
			if (!(x == arr.size() - 1)) {				
				result += ",";
			}
		}
		
		return new Message(result, MessageType.SUCCESS);
	}
	
	public Message withdrawAmount(String accountID, String amount) {
		Account a;

		if ('0' == accountID.charAt(1)) { // savings
			a = (SavingAccount) fileIO.readAccount(accountID + ".txt");
		} else {
			a = (CheckingAccount) fileIO.readAccount(accountID + ".txt");
		}
		
		Boolean canWithdraw = true;
		
		// if can't withdraw, return error
		if (!a.withdraw(Double.parseDouble(amount))) {
			canWithdraw = false;
		}
		
		fileIO.writeAccount(accountID, a);
		
		String result = "";
		ArrayList<String> arr = a.filePrep();
		
		for (int x = 0; x < arr.size(); x++) {
			result += arr.get(x);
			if (!(x == arr.size() - 1)) {				
				result += ",";
			}
		}
		
		if (canWithdraw) {		
			return new Message(result, MessageType.SUCCESS);
		} else {
			return new Message(result, MessageType.ERROR);
		}
	}
	
	public Message transferAmount(String accountID, String targetAccountID, String amount) {
		
		// THIS CODE IS TOTALLY WRONG
		// transfer function requires the target Account, the message needs to send
		// the account id of the user and it's target account.
		Account a;

		if ('0' == targetAccountID.charAt(1)) { // savings
			a = (SavingAccount) fileIO.readAccount(targetAccountID + ".txt");
		} else {
			a = (CheckingAccount) fileIO.readAccount(targetAccountID + ".txt");
		}
		
		Boolean canTransfer = true;
		
		// if can't withdraw, return error
		if (!a.transferFunds(targetAccountID, Double.parseDouble(amount))) {
			canTransfer = false;
		}
		
		fileIO.writeAccount(targetAccountID, a);
		
		String result = "";
		ArrayList<String> arr = a.filePrep();
		
		for (int x = 0; x < arr.size(); x++) {
			result += arr.get(x);
			if (!(x == arr.size() - 1)) {				
				result += ",";
			}
		}
		
		if (canTransfer) {		
			return new Message(result, MessageType.SUCCESS);
		} else {
			return new Message(result, MessageType.ERROR);
		}
		
		// THIS CODE IS TOTALLY WRONG
	}
}













































