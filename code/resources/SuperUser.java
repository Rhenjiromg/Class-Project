package resources;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class SuperUser extends Operator{
    private String name;
    private String ID;
    private String password;
    private FileIO fileIO;

    public SuperUser(String name, String password) {
        this.name = name;
        this.password = password;
        
        Account acc = new Account();
        User u = new User();
        
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void deleteAccount(String userID, String accID) {
		Operator op = null;

		try {
			op = fileIO.readOperator("U" + userID + ".txt");
			//Check if account exists.
			if(op.getAccList().contains(accID)) {
				//Remove the account from the user's list
				op.removeAccount(accID);
				System.out.println(accID + "deleted from user.");

			}
			else {
				//In case user doesn't exist. Throw this message.
				throw new UnauthorizedAccessException(accID + "does not exist for user " + userID + ".");
			}
		} catch(FileNotFoundException e) {
			//file for user not was not found
			System.out.println("User" + userID + "does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		try {
			//Saved the updated user data to the txt
			fileIO.writeOperator("U" + userID, op);
			System.out.println("Updates have been made to " + userID + "'s files.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void addPerson(String userID, String accID) {
       //Added logic
        Pattern pattern = Pattern.compile("^A0");
        Pattern pattern2 = Pattern.complie("^A1");

        Operator op = null; 
        /* 1. call readUser to get a user from the file, use the condition of filename("U" + userID
         * in a try catch or an if clause, so that if the file doesn't exist then we call
         * the user constructor to create a new user instead.
         * 
         * 2. Wait for addAccount and removeAccount into the user class
         * to be used for adding the accountID to the new user object
         * 
         * 3. call writeUser to save the change to file 
        ) */

        try {
      /* Call readOperator to attempt to get data from file */
           op = fileIO.readOperator("U" + userID + ".txt"); 

        //Check if account and user already exist
        if(user.getAccList().contains(accID)) {
            //user already exists and an account already exists
            System.out.println("The user and account already exist.");
        }
        else {
            //If not, add to account.
            op.addAccount(accID);
        }
           
    } catch (FileNotFoundException e) {
      /* The file was not found so create a new user */

      op = new User(name, password); //call the user constructor

      /* Where addAccount/removeAccount will be */
      op.addAccount(accID);

      System.out.println("File was not found... New user created.");
    } catch (IOException e) { //Error message
        e.printStackTrace();
        return;
    }

    try { 
    	fileIO.writeOperator("U" + userID, op);
        System.out.println("User has been saved.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    public void openAccount() {
        Account acc = new Account();
        Pattern pattern = Pattern.compile("^A0");
        Pattern pattern2 = Pattern.complie("^A1");

        //Figure out if its a savings or a checkings
        if(pattern.matcher(ID).find()) {
            SavingAccount acc = new SavingAccount();
        }
        else if(pattern2.matcher(ID).find()) {
            CheckingAccount acc = new CheckingAccount();
        }

        //Add the account to the user object
        user.addAccount(ID); 
        fileIO.writeAccount(acc.getAccountID() + ".txt", acc);

        System.out.println("Opening an account." + name + " " + password);
    }

    //For the JUnit tests
    public String getName() {return name;}
    public String getPassword() {return password;}
    public String getID() {return ID;}
}
