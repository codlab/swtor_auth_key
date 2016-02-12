package eu.codlab.swtor.utils.test_lock.non_test;

/**
 * Created by Kévin on 05/02/2016.
 */
public class LockableMaintainer {
    private Object _retained;

    public void retain(Object retain) {
        if (null != _retained || null == retain)
            throw new IllegalStateException("sorry... try again");
        _retained = retain;
    }

    public void release(Object retain) {
        if (null == _retained || null == retain) throw new IllegalStateException("you failed");

        if (_retained == retain) _retained = null;
        else throw new IllegalStateException("sorry... try again");
    }
}
