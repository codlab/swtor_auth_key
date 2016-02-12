package eu.codlab.swtor.internal.security;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
public class TimeProviderTest {

    @Test
    public void testResume() throws NoSuchFieldException, IllegalAccessException {
        TimeProvider provider = new TimeProvider();
        Field mHandler = provider.getClass().getDeclaredField("mHandler");
        mHandler.setAccessible(true);

        Field mPostEvent = provider.getClass().getDeclaredField("mPostEvent");
        mPostEvent.setAccessible(true);

        assertNotNull(mPostEvent.get(provider));

        provider.onResume();
        assertNotNull(mHandler.get(provider));
        assertTrue(provider.isResumed());
        assertFalse(provider.isPaused());

        provider.onPause();
        assertNull(mHandler.get(provider));
        assertTrue(provider.isPaused());
        assertFalse(provider.isResumed());

        provider.onResume();
        assertNotNull(mHandler.get(provider));
        assertTrue(provider.isResumed());
        assertFalse(provider.isPaused());
    }

    @Test
    public void testgetCurrentIterationStat() {
        TimeProvider provider = new TimeProvider();

        long interval = provider.getCurrentInterval() * SecurityConstants.INTERVAL;
        assertEquals(interval, provider.getCurrentIterationStart());
    }

    @Test
    public void testGetNextIterationIn() {
        TimeProvider provider = new TimeProvider();
        long nextIteration = provider.getNextIteration() - System.currentTimeMillis();

        long diff = Math.abs(nextIteration - provider.getNextIterationIn());

        assertTrue(diff < 10);
    }

    @Test
    public void testGetNextIteration() {
        TimeProvider provider = new TimeProvider();

        long nextIteration = provider.getCurrentIterationStart() + SecurityConstants.INTERVAL + 1;

        assertEquals(nextIteration, provider.getNextIteration());
    }

    @Test
    public void testGetCurrentInterval() {
        TimeProvider provider = new TimeProvider();

        long currentInterval = System.currentTimeMillis() / SecurityConstants.INTERVAL;

        assertEquals(currentInterval, provider.getCurrentInterval());
    }

    @Test
    public void testPostNextIteration() {
        TimeProvider provider = new TimeProvider();

        assertFalse(provider.postNextIteration());

        provider.onResume();

        assertTrue(provider.postNextIteration());
    }

    @Test
    public void testInternalPostRunnable() {
        TimeProvider provider = new TimeProvider();
        provider.onResume();

        EventBus eventbus = DependencyInjectorFactory.getDependencyInjector()
                .getDefaultEventBus();

        assertNotNull(eventbus);

        eventbus.postSticky(new CodeInvalidateEvent(provider.getNextIterationIn()));

        assertNotNull(eventbus.getStickyEvent(CodeInvalidateEvent.class));

        assertTrue(provider.postNextIteration());
    }

    @Test
    public void testTimeProviderPrivateMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TimeProvider provider = new TimeProvider();
        provider.onResume();

        Method method = provider.getClass().getDeclaredMethod("createPostNextIterationRunnable");
        method.setAccessible(true);

        Runnable runnable = (Runnable) method.invoke(provider);
        assertNotNull(runnable);

        runnable.run();

        assertNotNull(DependencyInjectorFactory.getDependencyInjector().getDefaultEventBus()
                .getStickyEvent(CodeInvalidateEvent.class));
    }

    @Test
    public void testOnResume() {
        TimeProvider provider = new TimeProvider();

        provider.onPause();

        assertFalse(provider.isResumed());

        provider.onResume();

        assertTrue(provider.isResumed());
    }

    @Test
    public void testOnPause() {
        TimeProvider provider = new TimeProvider();

        provider.onPause();

        assertTrue(provider.isPaused());

        provider.onResume();

        assertFalse(provider.isPaused());
    }
}
