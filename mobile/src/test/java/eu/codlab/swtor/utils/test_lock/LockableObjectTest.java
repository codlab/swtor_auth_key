package eu.codlab.swtor.utils.test_lock;

import android.support.annotation.NonNull;

import org.junit.Test;

import eu.codlab.swtor.utils.LockableObject;
import eu.codlab.swtor.utils.test_lock.non_test.LockableMaintainer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kévin on 05/02/2016.
 */
public class LockableObjectTest {

    /**
     * Test the lock object
     *
     * It will iterate a large amount of time between two thread to make the :
     * lock / unlock process
     */
    @Test
    public void testLock() {
        //create a maintainer object
        final LockableMaintainer maintainer = new LockableMaintainer();
        final LockableObject test = new LockableObject();
        final boolean state[] = new boolean[]{false};

        Thread thread = new Thread() {
            @Override
            public void run() {
                retainLoop("thread", test, maintainer, 50);
                state[0] = true;
            }
        };
        thread.start();

        retainLoop("main", test, maintainer, 30);


        while (!state[0]) {
            wait(100);
        }

        assertEquals(true, state[0]);
    }

    /**
     * SImple method to make the proper wait call
     * <p/>
     * It will fire exception if something went wrong ... setting the call as excepted
     *
     * @param tag        a tag for outputs
     * @param locker     the locker object - in test
     * @param maintainer a maintainer object - tested here
     * @param timeout    how long to wait between loop(/2)
     */
    private void retainLoop(String tag, @NonNull LockableObject locker, @NonNull LockableMaintainer maintainer,
                            int timeout) {
        Object to_retain = new Object();
        for (int i = 0; i < 500; i++) {
            boolean ok = true;

            try {
                locker.lock();
                System.out.println("lock :: " + tag + " " + i);
                maintainer.retain(to_retain);
                wait(timeout);
                maintainer.release(to_retain);
                System.out.println("unlock :: " + tag + " " + i);
                locker.unlock();
                wait(timeout);
            } catch (IllegalStateException exception) {
                ok = false;
            }

            assertTrue(ok);
        }
    }

    /**
     * Make the calling thread or messaging holder to wait for "milli" milliseconds
     *
     * @param milli
     */
    private void wait(int milli) {
        try {
            Thread.sleep(milli);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
