package shared;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.regex.*;

//filler method call within this facade right now
public class FileIO {
    private final LockManager lockManager = LockManager.getInstance();

    public void writeAccount(String filePath, Account acc) {
        long writeStamp = lockManager.getWriteLock(filePath); //use handle to get lock for write
        Pattern pattern = Pattern.compile("^A0");//saving
        Pattern pattern2 = Pattern.compile("^A1");//checking

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            //this is pretty much pseudo code for planning and demonstration
            String[] content = acc.filePrep().toArray(new String[0]);
            for (String i : content){
                writer.write(i);
                writer.newLine();
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            //this is pretty much pseudo code for planning and demonstration
            String[] content = op.filePrep().toArray(new String[0]);
            for (String i : content){
                writer.write(i);
                writer.newLine();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lockManager.releaseLock(writeStamp, filePath); // Release
        }
    }

    // Read Account
    public Account readAccount(String filePath) {

        long stamp = lockManager.getOptimist(filePath); //get optimist long for reference
        Account account = null;
        Pattern pattern = Pattern.compile("^A0");//saving
        Pattern pattern2 = Pattern.compile("^A1");//checking

        //try with reader as resource will fail if file not exist and will close reader on exit
        //read without locking
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Read the account details from the file
            String accountID = reader.readLine(); //ID read
            String balance = reader.readLine(); // 2nd line - Balance
            String creationDate = reader.readLine(); // 3rd line - Creation Date
            String lastDate = reader.readLine(); // Fourth line - last Date
            if(pattern.matcher(accountID).find()){//saving
                String withdrawalCount = reader.readLine(); //5th
                account = new SavingAccount(accountID, balance, creationDate, lastDate, withdrawalCount);
            } else if(pattern2.matcher(accountID).find()){//checking
                account = new CheckingAccount(accountID, balance, creationDate, lastDate);
            }
            else{//not an account!
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //validate stamp to see if there is a write during the read operation
        //otherwise no read lock needed!
        if (!lockManager.validateStamp(filePath, stamp)){
            long readLock = lockManager.getReadLock(filePath);
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Read the account details from the file
                String accountID = reader.readLine(); //ID read
                String creationDate = reader.readLine(); // Second line - Creation Date
                String balance = reader.readLine(); // Third line - Balance
                String state = reader.readLine(); // Fourth line - State
                if(pattern.matcher(accountID).find()){//saving
                	String withdrawalCount = reader.readLine(); //5th
                    account = new SavingAccount(accountID, balance, creationDate, lastDate, withdrawalCount);
                } else if(pattern2.matcher(accountID).find()){//checking
                    String maintenantFee = reader.readLine();
                // TODO: add to this later to reflect the actual constructor + convert string to proper args
                    account = new CheckingAccount(accountID, balance, creationDate, lastDate);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } 
            finally {
                lockManager.releaseLock(readLock, filePath); // Release read lock
            }
        }
        return account;
    }

    // Read Operator
    public Operator readOperator(String filePath) {
        long stamp = lockManager.getOptimist(filePath); //get optimist long for reference
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
                ArrayList<String> buffer;
                String line; 
                while ((line = reader.readLine()) != null) { 
                    buffer.add(line);
                }
                
                operator = new User(name, id, password, state, buffer);
            }else{
                //TODO: add proper construcotr when we did that modify
                operator = new SuperUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        if (!lockManager.validateStamp(filePath, stamp)){
            long readLock = lockManager.getReadLock(filePath); // Get read lock
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                // Read the operator details from the file
                String name = reader.readLine(); // First line - Name
                String id = reader.readLine(); // Second line - ID
                String password = reader.readLine(); // Third line - Password
                String state = reader.readLine(); // Fourth line - State
                if(pattern.matcher(id).find()){
                	ArrayList<String> buffer;
                    String line; 
                    while ((line = reader.readLine()) != null) { 
                        buffer.add(line);
                    }
                    
                    operator = new User(name, id, password, state, buffer);
                }else{
                    //TODO: add proper construcotr when we did that modify
                    operator = new SuperUser();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } 
            finally {
            lockManager.releaseLock(readLock, filePath); // Release read lock
            }
        }
        return operator;
    }

    //Write Log
    //example: if this is call inside a server facade method for account, logname will be account.getID 
    private void writeLog(String logname, String status){
        
        Time time;
        String logPath = "L" + logname + ".txt";
        String log = time.getCurrentTime() + ": " + status;

        long writeStamp = lockManager.getWriteLock(logPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logPath, true))) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lockManager.releaseLock(writeStamp, logPath); // Release the write lock
        }
    }
}
