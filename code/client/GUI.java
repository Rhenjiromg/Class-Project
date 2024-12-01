package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.swing.border.EmptyBorder;

import shared.*;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import shared.Account;
import shared.Message;
import shared.MessageType;
import shared.Operator;
import shared.User;

// ADD ACCOUNT AND OPERATOR WHEN DONE.

public class GUI {
	private Operator op;
	private Account acc;
	JButton[] accountButtons;
	private Queue<Message> outbound;
	String accID = ""; // this is where the accID is added to from displayScroll buttons (acc selector)
	boolean isLogin = false;
	String request;

	private synchronized void addQueue(Queue<Message> queue, Message message) {
		queue.add(message);
		queue.notify();
	}

	private synchronized Message popQueue(Queue<Message> queue) {
		return queue.poll();
	}

	public void setOp(Operator o) {

	}

	private final String[] userDisplay = {
			"Account Detail",
			"Transaction History",
			"Deposit",
			"Withdraw",
			"Transfer",
			"Make me Millionaire"
	};
	private final String[] superUserDisplay = {
			"Add User",
			"Create Account",
			"Deactivate Account",
			"Add User to Existing Account",
			"Check User"
	};

	// add operator, user
	// add account

	private JPanel mainPanel = new JPanel(new GridLayout(1, 3));
	private JPanel infoPanel = new JPanel(); // for displaying user information
	private JPanel displayPanel = new JPanel(new GridLayout(0, 1)); // for displaying information

	// returns String Array, array[0] = name, array[1] = password;
	public Message login() {
		Message handshake = new Message();
		JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
		JLabel userNameLabel = new JLabel("Username: ");
		JTextField userNameField = new JTextField(10);
		JLabel passwordLabel = new JLabel("Password: ");
		JTextField passwordField = new JTextField(10);

		// add components to panel
		panel.add(userNameLabel);
		panel.add(userNameField);
		panel.add(passwordLabel);
		panel.add(passwordField);

		// custom buttons for login and cancel
		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");

		// Create a custom option pane
		JOptionPane optionPane = new JOptionPane(
				panel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				new Object[] { loginButton, cancelButton }, loginButton);

		while (!isLogin) {
			// Open a dialog from the option pane
			JDialog dialog = optionPane.createDialog("Login Screen");

			loginButton.addActionListener(e -> {
				optionPane.setValue(loginButton); // record login press
				dialog.dispose();
			});

			cancelButton.addActionListener(e -> {
				optionPane.setValue(cancelButton); // record cancel press
				dialog.dispose();
			});

			dialog.setVisible(true);

			// if login pressed return text field values.
			if (optionPane.getValue() == loginButton) {
				String username = userNameField.getText();
				String password = new String(passwordField.getText());
				String data = username + " " + password;
				handshake = new Message(data, MessageType.LOGIN);
			}
		}

		return handshake;
	}

	public void userDisplay(Operator o, Queue<Message> queue) {
		op = o;
		JFrame frame = new JFrame("User Display");
		frame.setSize(800, 800);

		// dispose it if super user calls it
		// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel = new JPanel(new GridLayout(1, 3)); // main Panel
		JPanel mainButtonPanel = new JPanel();

		JPanel buttonPanel = new JPanel(new GridLayout(userDisplay.length, 0)); // for buttons

		// displayInfoPanel in scroll-able form (updated by client)
		// infoPanel in scroll-able form (updated by client)
		JScrollPane displayScroll = getScrollableDisplayPanel(op);
		JScrollPane userInfoScroll = getScrollableInfoPanel(op);

		// Add Buttons to button Panel with Action Listener
		accountButtons = new JButton[userDisplay.length];
		for (int i = 0; i < userDisplay.length; i++) {
			String option = userDisplay[i];
			JButton button = new JButton(option);
			accountButtons[i] = button; // store button in array for global reference
			button.setEnabled(false); // Initially disabled
			button.addActionListener(e -> {
				methodCaller(option, request);
			});
			button.setFont(new Font("Courier New", Font.BOLD, 15));
			button.setPreferredSize(new Dimension(250, 50));
			buttonPanel.add(button);
		}

		mainButtonPanel.add(buttonPanel); // panel for button panel (for sizing purposes)

		mainPanel.add(mainButtonPanel); // left 0
		mainPanel.add(displayScroll); // center 1
		mainPanel.add(userInfoScroll); // right 2

		frame.add(mainPanel);

		frame.setLocationRelativeTo(null); // center
		frame.setVisible(true); // make visible
	}

	// TODO: superGUI not done, need revise
	public void superUserDisplay() {
		JFrame frame = new JFrame("Super User Display");
		frame.setSize(950, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainButtonPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(superUserDisplay.length, 0)); // for buttons

		for (String option : superUserDisplay) {
			JButton button = new JButton(option);
			button.addActionListener(e -> {
				methodCaller(option, request);
			});
			button.setFont(new Font("Courier New", Font.BOLD, 15));
			button.setPreferredSize(new Dimension(300, 50));
			buttonPanel.add(button);
		}

		// displayInfoPanel in scroll-able form (updated by client)
		// infoPanel in scroll-able form (updated by client)
		// TODO: revamp su display method!
		JScrollPane displayScroll = getScrollableDisplayPanel(op.getAccount());
		JScrollPane userInfoScroll = getScrollableInfoPanel();

		mainButtonPanel.add(buttonPanel); // panel for button panel (for sizing purposes)

		mainPanel.add(mainButtonPanel); // left
		mainPanel.add(displayScroll); // center
		mainPanel.add(userInfoScroll); // right

		frame.add(mainPanel);

		frame.setLocationRelativeTo(null); // center
		frame.setVisible(true); // make visible
	}

	// SOME FUNCTIONS NEED MERGING, REPETITIVE CODE
	// TODO: handle cancels
	private void methodCaller(String s, String r) {
		String data = op.getID() + "," + r;
		String buffer;
		switch (s) {
			case "Account Detail":
				// send request to server
				// info will be displayed by client using setDisplayPanelInfo() and
				// setuserPanelInfo()

				// display account info already sent by the server
				addQueue(outbound, new Message(data, MessageType.ACCOUNT_INFO));
				break;
			case "Transaction History":
				// send request to server
				// info will be displayed by client using setDisplayPanelInfo() and
				// setuserPanelInfo()
				outbound.addQueue(new Message(data, MessageType.TRANSACTION_HISTORY));
				break;
			case "Deposit":
				buffer = deposit();
				if (!buffer.equals("")) {
					data += buffer;
					Message result = new Message(data, MessageType.DEPOSIT);
					addQueue(outbound, result);
					break;
				}
				// else diposit cancel
				break;
			case "Withdraw":
				buffer = withdraw();
				if (!buffer.equals("")) {
					data += buffer;
					Message result = new Message(data, MessageType.WITHDRAW);
					addQueue(outbound, result);
					break;
				}
				break;
			case "Transfer":
				buffer = transfer();
				if (!buffer.equals("")) {
					data += buffer;
					Message result = new Message(data, MessageType.TRANSFER);
					addQueue(outbound, result);
					break;
				}
				break;

			// TODO: CONT FIXING LATER
			// ---------------------------- Super User ----------------------------
			case "Add User":

				// change Login to Sign up.
				Message result = login();
				addQueue(outbound, result);
				break;

			// MERGE ACCOUNT FUNCTIONS INTO 1.
			case "Create Account":
				addQueue(outbound, new Message(createAccount(), MessageType.CREATE_ACCOUNT));
				break;
			case "Deactivate Account":
				addQueue(outbound, new Message(deleteAccount(), MessageType.DEACTIVATE_ACCOUNT));
				break;
			case "Add User to Existing Account":

				// changes need to be made
				addQueue(outbound, new Message(createAccount(), MessageType.ADD_USER_TO_EXISTING_ACCOUNT));
				break;
			case "Check User":
				// Ask superUser: which user's account they want access?
				// open up user Display for that account.
				userDisplay(user, outbound); // TODO:hook from su to u, need revamp
				break;
			case "Make me Millionaire":
				displayMillionDollars();
				break;
		}

	}

	private String deposit() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Enter Amount: $");
		JTextField textField = new JTextField(10);

		panel.add(label);
		panel.add(textField);

		JButton depositButton = new JButton("Deposit");
		JButton cancelButton = new JButton("Cancel");

		JOptionPane optionPane = new JOptionPane(panel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				// custom buttons, default is deposit button
				new Object[] { depositButton, cancelButton }, depositButton);

		JDialog dialog = optionPane.createDialog("Deposit Amount");

		depositButton.addActionListener(e -> {
			optionPane.setValue(depositButton); // record deposit button press
			dialog.dispose();
		});

		cancelButton.addActionListener(e -> {
			optionPane.setValue(cancelButton); // record cancel button press
			dialog.dispose();
		});

		dialog.setVisible(true);

		// If button pressed was deposit, return text field value
		if (optionPane.getValue() == depositButton) {
			return textField.getText();
		}

		return ""; // if canceled then return empty string
	}

	private String withdraw() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Enter Amount: $");
		JTextField textField = new JTextField(10);

		panel.add(label);
		panel.add(textField);

		JButton withdrawButton = new JButton("Withdraw");
		JButton cancelButton = new JButton("Cancel");

		JOptionPane optionPane = new JOptionPane(panel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				// custom buttons, default is deposit button
				new Object[] { withdrawButton, cancelButton }, withdrawButton);

		JDialog dialog = optionPane.createDialog("Withdraw Amount");

		withdrawButton.addActionListener(e -> {
			optionPane.setValue(withdrawButton); // record deposit button press
			dialog.dispose();
		});

		cancelButton.addActionListener(e -> {
			optionPane.setValue(cancelButton); // record cancel button press
			dialog.dispose();
		});

		dialog.setVisible(true);

		// If button pressed was deposit, return text field value
		if (optionPane.getValue() == withdrawButton) {
			return textField.getText();
		}

		return ""; // if canceled then return empty string
	}

	private String transfer() {
		// Main panel with a vertical BoxLayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		// First row mainPanel
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel("Enter Amount: $");
		JTextField textField = new JTextField(10);
		row1.add(label);
		row1.add(textField);

		// Second row mainPanel

		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel accountLabel = new JLabel("Enter Account ID: ");
		JTextField accountText = new JTextField(10);
		row2.add(accountLabel);
		row2.add(accountText);

		// Add rows to the main panel
		mainPanel.add(row1);
		mainPanel.add(row2);

		JButton transferButton = new JButton("Transfer");
		JButton cancelButton = new JButton("Cancel");

		JOptionPane optionPane = new JOptionPane(mainPanel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				// custom buttons, default is deposit button
				new Object[] { transferButton, cancelButton }, transferButton);

		JDialog dialog = optionPane.createDialog("Transfer Amount");

		transferButton.addActionListener(e -> {
			optionPane.setValue(transferButton); // record deposit button press
			dialog.dispose();
		});

		cancelButton.addActionListener(e -> {
			optionPane.setValue(cancelButton); // record cancel button press
			dialog.dispose();
		});

		dialog.setVisible(true);

		// If button pressed was deposit, return text field value
		if (optionPane.getValue() == transferButton) {
			return textField.getText();
		}

		return ""; // if canceled then return empty string
	}

	private String createAccount() {
		// Main panel with a vertical BoxLayout
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		JPanel row1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel label = new JLabel("Enter User Name");
		JTextField textField = new JTextField(10);
		row1.add(label);
		row1.add(textField);

		JPanel row2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel accountLabel = new JLabel("Enter Account ID: ");
		JTextField accountText = new JTextField(10);
		row2.add(accountLabel);
		row2.add(accountText);

		mainPanel.add(row1);
		mainPanel.add(row2);

		JButton createAccountButton = new JButton("Create Account");
		JButton cancelButton = new JButton("Cancel");

		JOptionPane optionPane = new JOptionPane(mainPanel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				// custom buttons, default is deposit button
				new Object[] { createAccountButton, cancelButton }, createAccountButton);

		JDialog dialog = optionPane.createDialog("Create Account");

		createAccountButton.addActionListener(e -> {
			optionPane.setValue(createAccountButton); // record deposit button press
			dialog.dispose();
		});

		cancelButton.addActionListener(e -> {
			optionPane.setValue(cancelButton); // record cancel button press
			dialog.dispose();
		});

		dialog.setVisible(true);

		// If button pressed was deposit, return text field value
		if (optionPane.getValue() == createAccountButton) {
			return textField.getText();
		}

		return ""; // if canceled then return empty string
	}

	private String deleteAccount() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel label = new JLabel("Enter Account ID: ");
		JTextField textField = new JTextField(10);

		panel.add(label);
		panel.add(textField);

		JButton deleteAccountButton = new JButton("Delete Account");
		JButton cancelButton = new JButton("Cancel");

		JOptionPane optionPane = new JOptionPane(panel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				// custom buttons, default is deposit button
				new Object[] { deleteAccountButton, cancelButton }, deleteAccountButton);

		JDialog dialog = optionPane.createDialog("Delete Account");

		deleteAccountButton.addActionListener(e -> {
			optionPane.setValue(deleteAccountButton); // record deposit button press
			dialog.dispose();
		});

		cancelButton.addActionListener(e -> {
			optionPane.setValue(cancelButton); // record cancel button press
			dialog.dispose();
		});

		dialog.setVisible(true);

		// If button pressed was deposit, return text field value
		if (optionPane.getValue() == deleteAccountButton) {
			return textField.getText();
		}

		return ""; // if canceled then return empty string
	}

	private void displayMillionDollars() {
		String[] s = { "Balance: ", "$ 1,000,000" };
		setDisplayPanelInfo(s);
	}

	// Updates the display panel's info
	public void setDisplayPanelInfo(String[] s) {
		// text area for writing information
		JTextArea infoArea = new JTextArea(10, 10);

		for (String x : s) {
			infoArea.append(x);
			infoArea.append("\n");
		}

		// Assuming displayPanel is a container (like a JPanel), you might add the
		// JScrollPane to it:
		displayPanel.removeAll(); // Remove any existing components from displayPanel
		displayPanel.add(infoArea); // Add the updated scrollable infoArea to the panel
		displayPanel.revalidate(); // Revalidate the layout to reflect changes
		displayPanel.repaint(); // Repaint the panel to show the updated content
	}

	// Updates the user info
	public void setUserPanelInfo(String[] s) {
		JScrollPane displayScroll = getScrollableDisplayPanel(op);
	}

	private JScrollPane getScrollableInfoPanel(Operator op) {
		String[] data = op.getInfo().toArray(new String[0]);

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Add the data to the panel
		for (String info : data) {
			JLabel label = new JLabel(info);
			infoPanel.add(label);
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between labels
		} // Create a scroll pane to wrap the panel
		JScrollPane scrollPane = new JScrollPane(infoPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); // increase scroll speed
		scrollPane.setBorder(new EmptyBorder(10, 0, 10, 10));
		return scrollPane;
	}

	private JScrollPane getScrollableInfoPanel(Account acc) {
		String[] data = acc.filePrep().toArray(new String[0]);

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); // Add the data to the panel
		for (String info : data) {
			JLabel label = new JLabel(info);
			infoPanel.add(label);
			infoPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing between labels
		} // Create a scroll pane to wrap the panel
		JScrollPane scrollPane = new JScrollPane(infoPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); // increase scroll speed
		scrollPane.setBorder(new EmptyBorder(10, 0, 10, 10));
		return scrollPane;
	}

	private JScrollPane getScrollableDisplayPanel(Operator op2) {
		// TODO Auto-generated method stub
		return null;
	}

	private JScrollPane getScrollableDisplayPanel(User op2) {
		String[] data = op2.getAcc();
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
		// Vertical layout
		// Add buttons to the existing display panel
		for (String option : data) {
			JButton button = new JButton(option);

			button.addActionListener(e -> {
				// Handle the button, pass the accountID to string req!
				request = option;
				for (JButton but : accountButtons) {
					but.setEnabled(true); // Enable each button now that we selected an account to reference
				}
			});
			displayPanel.add(button);
			displayPanel.add(Box.createRigidArea(new Dimension(0, 5)));
			// Add spacing between buttons
		} // Create a scroll pane to wrap the panel
		JScrollPane scrollPane = new JScrollPane(displayPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); // Increase scroll speed
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 0));
		return scrollPane;
	}

	// TODO: THIS IS HANDLE TO UPDATE FROM OUT OF CLASS
	// caller need to make a user object with the new updated list of accounts
	// before calling
	public void updateAccountList(User u) {
		op = u;

		String[] accs = u.getAcc();
		// Remove the old JScrollPanes
		mainPanel.remove(1); // Removing the center JScrollPane (displayScroll)

		// Create updated JScrollPanes with new content
		JScrollPane updatedDisplayScroll = getScrollableDisplayPanel(u, accID); // TODO:fix later, involve method fixing

		// Add the new JScrollPanes
		mainPanel.add(updatedDisplayScroll, 1); // Add to center position (index 1)

		// Revalidate and repaint to ensure the UI is updated
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	// caller need to make an account type object with the new updated list of
	// accounts before calling
	public void updateAccount(Account a) {
		acc = a;
		// Remove the old JScrollPanes
		mainPanel.remove(2); // Removing the right JScrollPane (userInfoScroll)

		// Create updated JScrollPanes with new content
		JScrollPane updatedUserInfoScroll = getScrollableInfoPanel(acc);

		// Add the new JScrollPanes
		mainPanel.add(updatedUserInfoScroll, 2); // Add to right position (index 2)

		// Revalidate and repaint to ensure the UI is updated
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	// NOTE:AS OF RIGHT NOW GUI DOESNT IMPLEMENT SMART HANDLE FOR CONFLICT UPDATES
	// YET

	private String getIP() {
		String IP = "";
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			IP = inetAddress.getHostAddress();
		} catch (UnknownHostException e) {
			System.err.println("Unable to get IP address: " + e.getMessage());
		}
		return IP;
	}
}
