package server;

import java.util.ArrayList;

import shared.Account;
import shared.CheckingAccount;
import shared.FileIO;
import shared.Message;
import shared.MessageType;
import shared.SavingAccount;
import shared.User;

public class ServerFacade {
	FileIO fileIO = new FileIO();
	
	public Boolean autherize(String accountID) {
		User u = (User) fileIO.readOperator(accountID + ".txt");
		return u.getAccList().contains(accountID);
	}

	public Message depositAmount(String accountID, String amount) {

		if (!autherize(accountID)) {
			fileIO.writeLog(accountID, "Unauthorized access attempt.");
			return new Message(MessageType.ERROR);
		}
		
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
		if (!autherize(accountID)) {
			fileIO.writeLog(accountID, "Unauthorized access attempt.");
			return new Message(MessageType.ERROR);
		}
		
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
		
		if (!autherize(accountID)) {
			fileIO.writeLog(accountID, "Unauthorized access attempt.");
			return new Message(MessageType.ERROR);
		}
		
		// transfer function requires the target Account, the message needs to send
		// the account id of the user and it's target account.
		Account a;
		Account t;

		if ('0' == accountID.charAt(1)) { // savings
			a = (SavingAccount) fileIO.readAccount(accountID + ".txt");
		} else {
			a = (CheckingAccount) fileIO.readAccount(accountID + ".txt");
		}

		if ('0' == targetAccountID.charAt(1)) { // savings
			t = (SavingAccount) fileIO.readAccount(targetAccountID + ".txt");
		} else {
			t = (CheckingAccount) fileIO.readAccount(targetAccountID + ".txt");
		}

		Boolean canTransfer = true;

		// if can't withdraw, return error
		if (!a.transferFunds(t, Double.parseDouble(amount))) {
			canTransfer = false;
		}
		
		fileIO.writeAccount(accountID, a);
		fileIO.writeAccount(targetAccountID, t);
		

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
	}

	public Message transactionHistory(String accountID) {
		if (!autherize(accountID)) {
			fileIO.writeLog(accountID, "Unauthorized access attempt.");
			return new Message(MessageType.ERROR);
		}
		return new Message(fileIO.readLog(accountID), MessageType.SUCCESS);
	}

	// this function adds the user to a the specified account, if the user already exists it returns an error message
	public Message addUser(String accountID, String superUserID, String username, String password) {
		
		// if function not called by super user
		if ('1' != superUserID.charAt(1)) {
			fileIO.writeLog(superUserID, "Attempt made by non super user");
			return new Message(MessageType.ERROR);
		}
		
		User u = new User(username, password);
		
		if (u.Authorize(accountID)) {
			// if user already has the account
			return new Message(MessageType.ERROR);
		}
		
		u.addAccount(accountID); // else update user account list
		fileIO.writeOperator(accountID + ".txt", u); // update operator text file
		return new Message(MessageType.SUCCESS);
	}
	
	public Message createAccount(String accountID, String superUserID) {
		
		// if function not called by super user
		if ('1' != superUserID.charAt(1)) {
			fileIO.writeLog(superUserID, "Attempt made by non super user");
			return new Message(MessageType.ERROR);
		}
		
		Account a = new Account();
		fileIO.writeAccount(a.getAccountID() + ".txt", a);
		return new Message(MessageType.SUCCESS);
	}
	
	public Message deactivateAccount(String accountID, String superUserID) {

		// if function not called by super user
		if ('1' != superUserID.charAt(1)) {
			fileIO.writeLog(superUserID, "Attempt made by non super user");
			return new Message(MessageType.ERROR);
		}
		
		// find the account and delete that file
		// or change the state of account to inactive
		return new Message(MessageType.SUCCESS);
	}
}
