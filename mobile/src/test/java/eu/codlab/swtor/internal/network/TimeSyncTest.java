package eu.codlab.swtor.internal.network;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.TestUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class TimeSyncTest {

    private TimeSync mProvider;

    @Before
    public void init() {
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
    public void testSync(){
        TimeSync timeSync = TimeSync.getInstance();
        
        timeSync.deinit();
        assertFalse(timeSync.sync());

        timeSync.init();
        assertTrue(timeSync.sync());
    }
}
