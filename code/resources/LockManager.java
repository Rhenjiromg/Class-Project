package resources;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.StampedLock;

public class LockManager {
    private static final LockManager instance = new LockManager();
    private final ConcurrentHashMap<String, StampedLock> lockMap = new ConcurrentHashMap<>();

    private LockManager() {
    }

    public static LockManager getInstance() {
        return instance;
    }

    // Return StampedLock instead of Lock
    public StampedLock getLock(String filePath) {
        return lockMap.computeIfAbsent(filePath, path -> new StampedLock());
        //if the key filePath is present, 
        //return its value, 
        //else it create a new pair with the key and a new lock as value and 
        //return the created lock
    }

    // Handles:
    // write lock
    public long getWriteLock(String filePath) {
        StampedLock lock = getLock(filePath);
        return lock.writeLock();
    }

    // read lock
    public long getReadLock(String filePath) {
        StampedLock lock = getLock(filePath);
        return lock.readLock();
    }

    // release a lock
    public void releaseLock(long stamp, String filePath) {
        StampedLock lock = getLock(filePath);
        if (StampedLock.isWriteLockStamp(stamp)) {
            lock.unlockWrite(stamp);
        } else {
            lock.unlockRead(stamp);
        }
    }
}

