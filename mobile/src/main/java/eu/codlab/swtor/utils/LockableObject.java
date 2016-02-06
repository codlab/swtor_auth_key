package eu.codlab.swtor.utils;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kevinleperf on 25/01/16.
 */
public class LockableObject {
    private ReentrantLock mReentrantLock;

    public LockableObject(){
        mReentrantLock = new ReentrantLock();
    }

    public void lock() {
        mReentrantLock.lock();
    }

    public void unlock() {
        mReentrantLock.unlock();
    }

}
