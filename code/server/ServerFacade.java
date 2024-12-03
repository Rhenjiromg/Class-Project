package server;

import java.io.File;
import java.util.ArrayList;

import shared.Account;
import shared.CheckingAccount;
import shared.FileIO;
import shared.Message;
import shared.MessageType;
import shared.SavingAccount;
import shared.SuperUser;
import shared.User;

public class ServerFacade {
	private String userID;
	private Message result;

	private User u;

	public ServerFacade() {
		
	}
	
	public ServerFacade(String uID) {
		userID = uID;
	}
	
	FileIO fileIO = new FileIO();
	
	public Boolean authorize(String accID) {
		u = (User) fileIO.readOperator(userID + ".txt");
		return u.getAccList().contains(accID);

	}
	
	public Message login(Message m) {
		String[] datas = m.getList();
		userID = datas[0]; //keep for later, since we call login with every change of user we looking at anyway
		if (datas[0].charAt(1) == '1') {
			SuperUser su = (SuperUser) fileIO.readOperator(datas[0] + ".txt");
			if (su.authen(datas[1])) { //check password
				String data = String.join(",", su.filePrep());
				result = new Message(data, MessageType.LOGIN);
				return result; //user
			}
		}
		else {
			u = (User) fileIO.readOperator(datas[0] + ".txt");
			if (u.authen(datas[1])) { //check password
				String data = String.join(",", u.filePrep());
				result = new Message(data, MessageType.LOGIN);
				return result; //superuser
			}		
		}
		
		return result = new Message(MessageType.LOGIN); //i guess we can have client recognized message string = null as failed request.
	}

	
	public Message getInfo(Message m) {

		String[] datas = m.getMessage(); //userID 0, accID 1, other info 2
		if (!authorize(datas[1])) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message(String.join(",", u.filePrep()), MessageType.UPDATEERROR); //use this to update user on gui in case there is a change to accList

		}
		else {
			Account accBuf = fileIO.readAccount(datas[1] + ".txt");
			result = new Message(String.join(",", accBuf.filePrep()),MessageType.ACCOUNT_INFO);
			return result;
		}
	}

	public Message depositAmount(Message m) {
		String[] datas = m.getMessage(); //userID 0, accID 1, other info 2
		String buffer;
		buffer = (String) datas[2]; 
		if (!authorize(datas[1])) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message(String.join(",", u.filePrep()), MessageType.UPDATEERROR);

		}
		
		Account a;

		if ('0' == datas[1].charAt(1)) { // savings
			a = (SavingAccount) fileIO.readAccount(datas[1] + ".txt");
		} else {
			a = (CheckingAccount) fileIO.readAccount(datas[1] + ".txt");
		}

		
		a.deposit(Double.parseDouble(buffer));
		fileIO.writeAccount(datas[1] + ".txt", a);
		fileIO.writeLog(userID, datas[1] + ":Deposit success: " + buffer);
		fileIO.writeLog(datas[1], "Deposit success: " + buffer);

		
		ArrayList<String> arr = a.filePrep();

		String data = String.join(",", arr);
		result = new Message(data, MessageType.DEPOSIT);

		return result;
	}
	//for withdraw ig all we do is log them down if overdraft and dont decrease it
	public Message withdrawAmount(Message m) {
		String[] datas = m.getMessage(); //userID 0, accID 1, other info 2 //userID 0, accID 1, other info 2
		String buffer;
		buffer = (String) datas[2]; 
		if (!authorize(datas[1])) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message(String.join(",", u.filePrep()), MessageType.UPDATEERROR);

		}
		
		Account a;

		if ('0' == datas[1].charAt(1)) { // savings
			a = (SavingAccount) fileIO.readAccount(datas[1] + ".txt");
		} else {
			a = (CheckingAccount) fileIO.readAccount(datas[1] + ".txt");
		}

		ArrayList<String> arr = new ArrayList<String>();

		// if can't withdraw, return error
		if (!a.withdraw(Double.parseDouble(buffer))) {
			fileIO.writeLog(userID, datas[1] + ":Withdraw failed: overdraft.");
			fileIO.writeLog(datas[1], "Withdraw failed: overdraft.");
		}
		else {
			fileIO.writeAccount(datas[1] + ".txt", a);
			fileIO.writeLog(userID, datas[1] + ":Withdraw success: " + buffer);
			fileIO.writeLog(datas[1], "Withdraw success: " + buffer);
		}
		
		 arr = a.filePrep();

		String data = String.join(",", arr);
		result = new Message(data, MessageType.WITHDRAW);
		return result;
	}

	public Message transferAmount(Message m) {
		String[] datas = m.getMessage(); //userID 0, accID 1, accID2 2, other info 3
		String buffer;
		buffer = (String) datas[3]; 
		if (!authorize(datas[1])) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message(String.join(",", u.filePrep()), MessageType.UPDATEERROR);

		}
		
		// transfer function requires the target Account, the message needs to send
		// the account id of the user and it's target account.
		Account acc1;
		Account acc2;

		if ('0' == datas[1].charAt(1)) { // savings
			acc1 = (SavingAccount) fileIO.readAccount(datas[1] + ".txt");
		} else {
			acc1 = (CheckingAccount) fileIO.readAccount(datas[1] + ".txt");
		}

		if ('0' == datas[2].charAt(1)) { // savings
			acc2 = (SavingAccount) fileIO.readAccount(datas[2] + ".txt");
		} else {
			acc2 = (CheckingAccount) fileIO.readAccount(datas[2] + ".txt");
		}
		
		if(acc2 == null) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message("target account N/A", MessageType.ERROR);
		}

		// if can't withdraw, return error
		if (!acc1.transferFunds(acc2, Double.parseDouble(buffer))) {
			fileIO.writeLog(userID, datas[1] + ":Transfer failed: overdraft.");
			fileIO.writeLog(datas[1], "Transfer failed: overdraft.");
		}else {
			fileIO.writeAccount(datas[1] + ".txt", acc1);
			fileIO.writeAccount(datas[2] + ".txt", acc2);
			fileIO.writeLog(userID, datas[1] + ":transfer success to" + datas[2] + ": " + buffer);
			fileIO.writeLog( datas[1] , "transfer success to " + datas[2] + ": " + buffer);
			fileIO.writeLog(datas[2], "transfer success from " + datas[1] + ": " + buffer);
		}
		
		ArrayList<String> arr = acc1.filePrep();
		arr = acc1.filePrep();

		String data = String.join(",", arr);
		result = new Message(data, MessageType.WITHDRAW);
		return result;
	}
	public Message transactionHistory(Message m) {
		String[] datas = m.getMessage(); //userID 0, accID 1, other info 2
		if (!authorize(datas[1])) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message(String.join(",", u.filePrep()), MessageType.UPDATEERROR);

		}
		return new Message(fileIO.readLog(datas[1]), MessageType.TRANSACTION_HISTORY);
	}


	// leverage login to show user display for user operation, this method is unneed
	/*
	public Message addUser(Message m) {
		String[] datas = m.getMessage(); //superUserID 0, username 1, password 2
		// if function not called by super user...this wont be a case since non user has no access to superuser panel
		
		if ('1' != datas[0].charAt(1)) {

			fileIO.writeLog(superUserID, "Attempt made by non super user");
			return new Message(MessageType.ERROR);
		}
		
		

		User u = new User(datas[1], datas[2]);
		
		//...new user has no accounts!

		if (u.Authorize(datas[1])) {
			// if user already has the account
			return new Message("ADD_USER", MessageType.ERROR);
		}
		

		fileIO.writeOperator(u.getID() + ".txt", u); // update operator text file
		return new Message(MessageType.ADD_USER);
	}
	*/
	
	public Message addAccount(Message m) {

		String[] datas = m.getMessage(); //accID 0
		File file = new File(datas[0] + ".txt");
		
		
		u = (User) fileIO.readOperator(userID + ".txt");
		Account acc;
		if (file.exists()) {
			u.addAccount(datas[1]);
		} else {
			if ('0' == datas[1].charAt(1)) { // savings
				acc = new SavingAccount();
				 fileIO.writeAccount(acc.getAccountID() + ".txt", acc);
			} else {
				acc = new CheckingAccount();
				fileIO.writeAccount(acc.getAccountID() + ".txt", acc);
			}			
		}
		fileIO.writeOperator(userID + ".txt", u);
		String data = String.join(",", u.filePrep());
		return new Message(data, MessageType.UPDATEERROR);

	}

	
	public Message deactivateAccount(Message m) {
		String[] datas = m.getMessage(); //userID 0, accID 1, other info 2

		
		if (!authorize(datas[1])) {
			fileIO.writeLog(userID, datas[1] + ": Unauthorized access attempt.");
			return result = new Message(String.join(",", u.filePrep()), MessageType.UPDATEERROR);
		} else {
			u.popAcc(datas[1]);
			fileIO.writeOperator(userID, u);
			String data = String.join(",", u.filePrep());
			return new Message(data, MessageType.UPDATEERROR);
		}		
	}
}
