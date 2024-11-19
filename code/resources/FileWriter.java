import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.StampedLock;

public class FileWriter {
    private final FileLockManager lockManager = new FileLockManager();

    public void writeAccount(String filePath, Account acc) {
        StampedLock lock = lockManager.getLock(filePath); // Get the lock for the specific file
        long stamp = lock.writeLock(); // Acquire a write lock
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            //this is pretty much pseudo code for planning and demonstration
            writer.write(acc.getID());
            writer.newLine();
            writer.write(acc.getBalance().toString());
            writer.newLine();
            writer.write(acc.getDate().toString());
            writer.newLine();
            writer.write(acc.getState().toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamp); // Release the write lock
        }
    }

    public void writeOperator(String filePath, Operatot op){
        StampedLock lock = lockManager.getLock(filePath); // Get the lock for the specific file
        long stamp = lock.writeLock(); // Acquire a write lock
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlockWrite(stamp); // Release the write lock
        }
    }
}
