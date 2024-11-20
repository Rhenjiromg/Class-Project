import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.regex.*;

import resources.CheckingAccount;
//filler method call within this facade right now
public class FileIO {
    private final LockManager lockManager = LockManager.getInstance();

    public void writeAccount(String filePath, Account acc) {
        long writeStamp = lockManager.getWriteLock(filePath); //use handle to get lock for write
        Pattern pattern = Pattern.compile("^A0");//saving
        Pattern pattern2 = Pattern.compile("^A1");//checking

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            //this is pretty much pseudo code for planning and demonstration
            writer.write(acc.getAccountID());
            writer.newLine();
            writer.write(acc.getCreationDate().toString());
            writer.newLine();
            writer.write(String.valueOf(acc.getBalance()));
            writer.newLine();
            writer.write(acc.getState().toString());
            writer.newLine();
            if(pattern.matcher(accountID).find()){//saving
                writer.write(String.valueOf(acc.getWithdrawalCount()));
                writer.newLine();
                writer.write(String.valueOf(acc.getRate()));
                writer.newLine();
            } else if(pattern2.matcher(accountID).find()){//checking
                writer.write(String.valueOf(acc.getFee()));
                writer.newLine();
            }
            else{//not an account!
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lockManager.releaseLock(writeStamp, filePath); // Release the write lock
        }
    }

    public void writeOperator(String filePath, Operator op){
        long writeStamp = lockManager.getWriteLock(filePath); //get lock
        Pattern pattern = Pattern.compile("^U0");//user

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            //this is pretty much pseudo code for planning and demonstration
            writer.write(op.getName());
            writer.newLine();
            writer.write(op.getID());
            writer.newLine();
            writer.write(op.getPassword());
            writer.newLine();
            writer.write(op.getState().toString());
            writer.newLine();
            if(pattern.matcher(op.getID()).find()){ //checkID for user
                for (String element : op.getAccount()) { //write the whole account table
                    writer.write(element);
                    writer.newLine();
                } // Write each element on a new line
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lockManager.releaseLock(writeStamp, filePath); // Release
        }
    }

    // Read Account
    public Account readAccount(String filePath) {
        long readStamp = lockManager.getReadLock(filePath); // Get read lock
        Account account = null;
        Pattern pattern = Pattern.compile("^A0");//saving
        Pattern pattern2 = Pattern.compile("^A1");//checking

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the account details from the file
            String accountID = reader.readLine(); //ID read
            String creationDate = reader.readLine(); // Second line - Creation Date
            String balance = reader.readLine(); // Third line - Balance
            String state = reader.readLine(); // Fourth line - State
            if(pattern.matcher(accountID).find()){//saving
                String withdrawalCount = reader.readLine();
                String interestRate = reader.readLine();
            // TODO: add to this later to reflect the actual constructor + convert string to proper args
                account = new SavingAccount();
            } else if(pattern2.matcher(accountID).find()){//checking
                String maintenantFee = reader.readLine();
            // TODO: add to this later to reflect the actual constructor + convert string to proper args
                account = new CheckingAccount();
            }
            else{//not an account!
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lockManager.releaseLock(readStamp, filePath); // Release read lock
        }
        return account;
    }

    // Read Operator
    public Operator readOperator(String filePath) {
        long readStamp = lockManager.getReadLock(filePath); // Get read lock
        Operator operator = null;
        List<String> list = new ArrayList<>();

        Pattern pattern = Pattern.compile("^U0");//user

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the operator details from the file
            String name = reader.readLine(); // First line - Name
            String id = reader.readLine(); // Second line - ID
            String password = reader.readLine(); // Third line - Password
            String state = reader.readLine(); // Fourth line - State
            if(pattern.matcher(id).find()){
                String buffer;
                for (String element : op.getAccount()) { //write the whole account table
                    buffer = reader.readLine();
                    list.add(buffer);
                } // Write each element on a new line
                String[] accounts = list.toArray(new String[0]);
                //TODO: add proper construcotr when we did that modify
                operator = new User();
            }else{
                //TODO: add proper construcotr when we did that modify
                operator = new SuperUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lockManager.releaseLock(readStamp, filePath); // Release read lock
        }
        return operator;
    }
}

