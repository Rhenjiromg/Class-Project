import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;

public class LockManager {
    private static final LockManager instance = new LockManager();
    private final ConcurrentHashMap<String, Lock> lockMap = new ConcurrentHashMap<>();

    private LockManager() {
    }

    public static FileLockManager getInstance() {
        return instance;
    }

    public Lock getLock(String filePath) {
        Lock lock = lockMap.get(filePath); // Try to get the lock for filePath
        if (lock == null) {
            lock = new StampedLock();    // Create a new lock if none exists
            lockMap.put(filePath, lock);   // Put the new lock in the map
        }
        return lock;                       // Return the lock (existing or newly created)
    }
}
