package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Queue;

import javax.swing.border.EmptyBorder;

import resources.Message;
import resources.MessageType;

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

// ADD ACCOUNT AND OPERATOR WHEN DONE.

public class GUI {
	
	private final String[] userDisplay = {
			"Account",
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
	
	private JPanel userInfo = new JPanel(); // for displaying user information
	private JPanel displayPanel = new JPanel(new GridLayout(0, 1)); // for displaying information
	
	// returns String Array, array[0] = name, array[1] = password;
	public String[] login() {
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
				new Object[]{loginButton, cancelButton}, loginButton); 
		
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
			return new String[]{userNameField.getText(), passwordField.getText()}; 
		} 
		
		return new String[]{"", ""}; // return array of 2 empty strings if canceled.
	}
	
	
	public void userDisplay(Queue<Message> outbound) {
		JFrame frame = new JFrame("User Display");
		frame.setSize(800, 800);
		
		// dispose it if super user calls it
//		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new GridLayout(1, 3)); // main Panel
		JPanel mainButtonPanel = new JPanel();
		
		JPanel buttonPanel = new JPanel(new GridLayout(userDisplay.length, 0)); // for buttons
		
		// Add Buttons to button Panel with Action Listener
		for (String option: userDisplay) {
			JButton button = new JButton(option);
			button.addActionListener(e -> {
				methodCaller(option, outbound);
			});
			button.setFont(new Font("Courier New", Font.BOLD, 15));
			button.setPreferredSize(new Dimension(250, 50));
			buttonPanel.add(button);
		}
		
		// displayInfoPanel in scroll-able form (updated by client)
		// userInfo in scroll-able form (updated by client)
		JScrollPane displayScroll = getScrollableDisplayPanel();
		JScrollPane userInfoScroll = getScrollableUserPanel();
		
		mainButtonPanel.add(buttonPanel); // panel for button panel (for sizing purposes)
		
		mainPanel.add(mainButtonPanel); // left
		mainPanel.add(displayScroll); // center
		mainPanel.add(userInfoScroll); // right
		
		frame.add(mainPanel);
		
		
		frame.setLocationRelativeTo(null); // center
		frame.setVisible(true); // make visible
	}
	
	
	public void superUserDisplay(Queue<Message> outbound) {
		JFrame frame = new JFrame("Super User Display");
		frame.setSize(950, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainPanel = new JPanel(new GridLayout(1, 3)); // main Panel
		
		JPanel mainButtonPanel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(superUserDisplay.length, 0)); // for buttons
		
		for (String option: superUserDisplay) {
			JButton button = new JButton(option);
			button.addActionListener(e -> {
				methodCaller(option, outbound);
			});
			button.setFont(new Font("Courier New", Font.BOLD, 15));
			button.setPreferredSize(new Dimension(300, 50));
			buttonPanel.add(button);
		}
		
		// displayInfoPanel in scroll-able form (updated by client)
		// userInfo in scroll-able form (updated by client)
		JScrollPane displayScroll = getScrollableDisplayPanel();
		JScrollPane userInfoScroll = getScrollableUserPanel();
		
		mainButtonPanel.add(buttonPanel); // panel for button panel (for sizing purposes)
		
		mainPanel.add(mainButtonPanel); // left
		mainPanel.add(displayScroll); // center
		mainPanel.add(userInfoScroll); // right
		
		frame.add(mainPanel);
		
		
		frame.setLocationRelativeTo(null); // center
		frame.setVisible(true); // make visible
	}
	
// SOME FUNCTIONS NEED MERGING, REPETITIVE CODE
	private void methodCaller(String s, Queue<Message> outbound) {
		switch (s) {
			case "Account":
				// send request to server
				// info will be displayed by client using setDisplayPanelInfo() and setuserPanelInfo()
				
				// display account info already sent by the server
				outbound.add(new Message(MessageType.ACCOUNT_INFO));
				break;
			case "Transaction History":
				// send request to server
				// info will be displayed by client using setDisplayPanelInfo() and setuserPanelInfo()
				outbound.add(new Message(MessageType.TRANSACTION_HISTORY));
				break;
			case "Deposit":
				outbound.add(new Message(deposit(), MessageType.DEPOSIT));
				break;
			case "Withdraw":
				outbound.add(new Message(withdraw(), MessageType.WITHDRAW));
				break;
			case "Transfer":
				outbound.add(new Message(transfer(), MessageType.TRANSFER));
				break;
				
			// ---------------------------- Super User ----------------------------
			case "Add User":
				
				// change Login to Sign up.
				String[] s5 = login();
				outbound.add(new Message(s5[0] + s5[1], MessageType.ADD_USER));
				break;
				
// MERGE ACCOUNT FUNCTIONS INTO 1.
			case "Create Account":
				outbound.add(new Message(createAccount(), MessageType.CREATE_ACCOUNT));
				break;
			case "Deactivate Account":
				outbound.add(new Message(deleteAccount(), MessageType.DEACTIVATE_ACCOUNT));
				break;
			case "Add User to Existing Account":
				
				// changes need to be made
				outbound.add(new Message(createAccount(), MessageType.ADD_USER_TO_EXISTING_ACCOUNT));
				break;
			case "Check User":
				// Ask superUser: which user's account they want access?
				// open up user Display for that account.
				userDisplay(outbound);
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
				new Object[]{depositButton, cancelButton}, depositButton);
		
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
				new Object[]{withdrawButton, cancelButton}, withdrawButton);
		
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
		row2.add(accountLabel); row2.add(accountText); 
		
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
				new Object[]{transferButton, cancelButton}, transferButton);
		
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
	
	
	private String createAccount(){
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
		row2.add(accountLabel); row2.add(accountText); 
				
		mainPanel.add(row1);
		mainPanel.add(row2);

		JButton createAccountButton = new JButton("Create Account");
		JButton cancelButton = new JButton("Cancel");
		
		JOptionPane optionPane = new JOptionPane(mainPanel,
				JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				// custom buttons, default is deposit button
				new Object[]{createAccountButton, cancelButton}, createAccountButton);
		
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
				new Object[]{deleteAccountButton, cancelButton}, deleteAccountButton);
		
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
		String[] s = {"Balance: ", "$ 1,000,000"}; 
		setDisplayPanelInfo(s);
	}
	
	
	// Updates the display panel's info
	public void setDisplayPanelInfo(String[] s) {		
		// text area for writing information
		JTextArea infoArea = new JTextArea(10, 10);

		for (String x: s) {
			infoArea.append(x);
			infoArea.append("\n");
		}
		displayPanel.add(infoArea);
	}
	
	
	// Updates the user info
	public void setUserPanelInfo(String[] s) {
		// text area for writing user info
		JTextArea infoArea = new JTextArea(10, 10);
		
		for (String x: s) {
			infoArea.append(x);
			infoArea.append("\n");
		}
		userInfo.add(infoArea);
	}
	
	
	private JScrollPane getScrollableUserPanel() {
		JScrollPane scrollPane = new JScrollPane(userInfo);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); // increase scroll speed
		scrollPane.setBorder(new EmptyBorder(10, 0, 10, 10));
		return scrollPane;
	}
	
	
	private JScrollPane getScrollableDisplayPanel() {
		JScrollPane scrollPane = new JScrollPane(displayPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20); // increase scroll speed
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 0));
		return scrollPane;
	}
}









































