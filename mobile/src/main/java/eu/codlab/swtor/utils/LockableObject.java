package eu.codlab.swtor.utils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kevinleperf on 25/01/16.
 */
public class LockableObject {
    private ReentrantLock _reentrant_lock;

    public LockableObject(){
        _reentrant_lock = new ReentrantLock();
    }

    public void lock() {
        _reentrant_lock.lock();
    }

    public void unlock() {
        _reentrant_lock.unlock();
    }

}
