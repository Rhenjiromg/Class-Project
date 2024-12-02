package shared;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class SuperUser extends Operator{
    private String name;
    private String ID;
    private String password;
    private FileIO fileIO;

    //cannot create new superUser, must alr exist on database; simulate corporate assigned credential
    //this constructor from read from file only
    public SuperUser(String n, String id, String pass, String s) {
        super(n, id, pass, s);
    }

    public void deleteAccount(String userID, String accID) throws Exception {
		User user = null;

		try {
			user = (User) fileIO.readOperator(userID + ".txt");
			//Check if account exists.
			if(user.getAccList().contains(accID)) {
				//Remove the account from the user's list
				user.getAccList().remove(accID);
				//System.out.println(accID + "deleted from user.");

			}
			else {
				//In case user doesn't exist. Throw this message.
				throw new Exception(accID + "does not exist for user " + userID + ".");
			}
		} catch(FileNotFoundException e) {
			//file for user not was not found
			System.out.println("User" + userID + "does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		//Saved the updated user data to the txt
		fileIO.writeOperator(userID + ".txt", user);
		System.out.println("Updates have been made to " + userID + "'s files.");
	}

    public void addPerson(String userID, String accID) {
       //Added logic
        Pattern pattern = Pattern.compile("^A0");
        Pattern pattern2 = Pattern.compile("^A1");

        User user; 
        /* 1. call readUser to get a user from the file, use the condition of filename("U" + userID
         * in a try catch or an if clause, so that if the file doesn't exist then we call
         * the user constructor to create a new user instead.
         * 
         * 2. Wait for addAccount and removeAccount into the user class
         * to be used for adding the accountID to the new user object
         * 
         * 3. call writeUser to save the change to file 
        ) */

        /* Call readOperator to attempt to get data from file */
		user = (User) fileIO.readOperator(userID + ".txt"); 

		    //Check if account and user already exist
		if(user.getAccList().contains(accID)) {
		    //user already exists and an account already exists
		    System.out.println("The user and account already exist.");
		}
		else {
		    //If not, add to account.
			user.getAccList().add(accID);
		}

		 /* The file was not found so create a new user
	       * This case should be controlled and call creat new user request by server facade
	       * Or ideally block by GUI, return pop up that user is no longer available. */
		fileIO.writeOperator(userID + ".txt", user);
		System.out.println("User has been saved.");
}
    
    public void openAccount(User user) {
        Account acc = null;
        Pattern pattern = Pattern.compile("^A0");
        Pattern pattern2 = Pattern.compile("^A1");

        //Figure out if its a savings or a checkings
        if(pattern.matcher(ID).find()) {
             acc = new SavingAccount();
        }
        else if(pattern2.matcher(ID).find()) {
            acc = new CheckingAccount();
        }

        //Add the account to the user object
        user.addAccount(ID); 
        fileIO.writeOperator(user.getID()+ ".txt", user);
        fileIO.writeAccount(acc.getAccountID() + ".txt", acc);

        System.out.println("Opening an account." + name + " " + password);
    }

    //For the JUnit tests
    public String getName() {return name;}
    public String getPassword() {return password;}
    public String getID() {return ID;}
}
