package eu.codlab.swtor.utils.test_lock;

import org.junit.Test;

import eu.codlab.swtor.utils.test_lock.non_test.LockableMaintainer;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Kévin on 05/02/2016.
 */
public class LockableMaintainerTest {

    @Test
    public void testErrorDuringLock() {
        Object first = new Object();
        Object second = new Object();

        LockableMaintainer maintainer = new LockableMaintainer();

        boolean ok = true;

        try {
            maintainer.retain(first);
            maintainer.retain(second);
        } catch (IllegalStateException exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testErrorMultipleLock() {
        Object first = new Object();
        LockableMaintainer maintainer = new LockableMaintainer();
        boolean ok = true;
        try {
            maintainer.retain(first);
            maintainer.retain(first);
        } catch (IllegalStateException exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testErrorRetainNull() {
        LockableMaintainer maintainer = new LockableMaintainer();
        boolean ok = true;
        try {
            maintainer.retain(null);
        } catch (IllegalStateException exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testErrorReleaseNull() {
        LockableMaintainer maintainer = new LockableMaintainer();
        boolean ok = true;
        try {
            maintainer.retain(new Object());
            maintainer.release(null);
        } catch (IllegalStateException exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testErrorReleaseWithoutRetain() {
        LockableMaintainer maintainer = new LockableMaintainer();
        boolean ok = true;
        try {
            maintainer.release(null);
        } catch (IllegalStateException exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testOk() {
        LockableMaintainer maintainer = new LockableMaintainer();
        boolean ok = true;
        try {
            Object object = new Object();

            maintainer.retain(object);
            maintainer.release(object);
        } catch (IllegalStateException exception) {
            ok = false;
        }

        assertTrue(ok);
    }
}
