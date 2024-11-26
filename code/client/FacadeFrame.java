package client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import resources.Message;
import resources.MessageType;

/**
 * This is assuming theres a listener in client.
 */

public class FacadeFrame {
	private static final String SERVER_ADDRESS = "IP HERE";
	private static final int SERVER_PORT = 00;
	/** change with actual Port */
	private String sender;
	private String receiver;

	public FacadeFrame(String sender, String receiver) {
		this.sender = sender;
		this.receiver = receiver;
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

	public void login(String credentials, String password) {
		String loginCredentials = credentials + "," + password;
		sendMessage(loginCredentials, MessageType.LOGIN);
	}

	public void checkBalance(String accountNumber) {
		sendMessage("AccountNumber", MessageType.CHECKBALANCE);
	}

	public void logout() {
		sendMessage("Logout Message", MessageType.LOGOUT);
	}

	public void deposit(float amount) {
		String deposit = sender + "," + amount;
		sendMessage(deposit, MessageType.DEPOSIT);
	}

	public void checkTransactionHistory() {
		sendMessage(sender, MessageType.TRANSACTION_HISTORY);
	}

	public void transfer(String destination, float amount) {
		String message = destination + "," + amount;
		sendMessage(message, MessageType.TRANSFER);
	}

	public void withDraw(Float amount) {
		String message = amount.toString();
		sendMessage(message, MessageType.WITHDRAW);
	}

	/** Unsure about this */
	public void verify() {
		sendMessage(sender, MessageType.VERIFICATION);
	}
}
