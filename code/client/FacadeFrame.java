package client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import shared.Message;
import shared.MessageType;

/**
 * This is assuming theres a listener in client.
 */

public class FacadeFrame {
	private static final String SERVER_ADDRESS = "IP HERE";
	private static final int SERVER_PORT = 31415;
	/** change with actual Port */
	private String sender;
	private String receiver;

	public FacadeFrame(String sender) {
		this.sender = sender;
	}

	private void sendMessage(String messageContent, MessageType type) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
			Message message = new Message(messageContent, type);
			message.setSender(sender);
			message.setReceiver(receiver);
			out.writeObject(message);
			out.flush();

			System.out.println("Message sent successfully!");
		} catch (Exception e) {
			System.err.println("Error sending message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void sendMessage(MessageType type) {
		try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
			Message message = new Message("", type);
			message.setSender(sender);
			message.setReceiver(receiver);
			out.writeObject(message);
			out.flush();

			System.out.println("Message sent successfully!");
		} catch (Exception e) {
			System.err.println("Error sending message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void login(String credentials, String password) {
		String loginCredentials = credentials + "," + password;
		sendMessage(loginCredentials, MessageType.LOGIN);
	}

	public void logout() {
		sendMessage(MessageType.LOGOUT);
	}

	public void deposit(double amount) {
		String deposit = sender + "," + amount;
		sendMessage(deposit, MessageType.DEPOSIT);
	}

	public void checkTransactionHistory(String accNumber) {
		sendMessage(accNumber, MessageType.TRANSACTION_HISTORY);
	}

	public void doTransfer(String destination, double amount) {
		String message = destination + "," + amount;
		sendMessage(message, MessageType.TRANSFER);
	}

	public void withDraw(double amount) {
		String message = Double.toString(amount);
		sendMessage(message, MessageType.WITHDRAW);
	}

	public void addUser(String userID, String accountID) {
		String message = userID + "," + accountID;
		sendMessage(message, MessageType.ADD_USER);
	}

	public void accountInfo(String accountNumber) {
		sendMessage(accountNumber, MessageType.ACCOUNT_INFO);
	}

	public void createAccount(String password, String userCredentials) {
		String message = password + "," + userCredentials;
		sendMessage(message, MessageType.CREATE_ACCOUNT);
	}

	public void deactivateAccount(String accountNumber) {
		sendMessage(accountNumber, MessageType.DEACTIVATE_ACCOUNT);
	}

	public void addUserToExisting(String userID, String accountID) {
		String message = userID + "," + accountID;
		sendMessage(message, MessageType.ADD_USER_TO_EXISTING_ACCOUNT);
	}

	/** Unsure about this */
	public void verify() {
		sendMessage(MessageType.VERIFICATION);
	}
}
