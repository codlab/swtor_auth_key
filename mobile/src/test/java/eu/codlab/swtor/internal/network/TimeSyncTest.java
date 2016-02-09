package eu.codlab.swtor.internal.network;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import eu.codlab.swtor.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class TimeSyncTest {

    @Before
    public void init() {
    }

    @Test
    public void testFinal() {
        Class klass = TimeSync.class;

        assertTrue(Modifier.isFinal(klass.getModifiers()));
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertSingletonClassWellDefined(TimeSync.class);
    }

    @Test
    public void testStatic() {
        assertNotNull(TimeSync.getInstance());
    }

    @Test
    public void testInitDeinit() {
        TimeSync timeSync = TimeSync.getInstance();
        timeSync.deinit();

        assertFalse(timeSync.isInit());

        timeSync.init();

        assertTrue(timeSync.isInit());

        timeSync.deinit();
        assertFalse(timeSync.isInit());
    }

    @Test
    public void testSync() {
        TimeSync timeSync = TimeSync.getInstance();

        timeSync.deinit();
        assertFalse(timeSync.sync());

        timeSync.init();
        assertTrue(timeSync.sync());
    }

    @Test
    public void testSetDiff() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        long diff = 10;

        Method setDiff = TimeSync.class.getDeclaredMethod("setDiff", long.class);
        setDiff.setAccessible(true);

        TimeSync sync = TimeSync.getInstance();

        setDiff.invoke(sync, diff);

        assertEquals(diff, sync.getDiff());
    }
}
