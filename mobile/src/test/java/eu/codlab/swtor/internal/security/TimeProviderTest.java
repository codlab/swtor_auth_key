package eu.codlab.swtor.internal.security;

import org.junit.Test;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
public class TimeProviderTest {

    @Test
    public void testResume() {
        TimeProvider provider = new TimeProvider();

        provider.onResume();
        assertTrue(provider.isResumed());
        assertFalse(provider.isPaused());

        provider.onPause();
        assertTrue(provider.isPaused());
        assertFalse(provider.isResumed());

        provider.onResume();
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
    public void testGetNextIterationIn(){
        TimeProvider provider = new TimeProvider();
        long nextIteration = provider.getNextIteration() - System.currentTimeMillis();

        assertEquals(nextIteration, provider.getNextIterationIn());
    }

    @Test
    public void testGetNextIteration() {
        TimeProvider provider = new TimeProvider();

        long nextIteration = provider.getCurrentIterationStart() + SecurityConstants.INTERVAL + 1;

        assertEquals(nextIteration, provider.getNextIteration());
    }

    @Test
    public void testGetCurrentInterval(){
        TimeProvider provider = new TimeProvider();

        long currentInterval = System.currentTimeMillis()/SecurityConstants.INTERVAL;

        assertEquals(currentInterval, provider.getCurrentInterval());
    }

    @Test
    public void testPostNextIteration(){
        TimeProvider provider = new TimeProvider();

        assertFalse(provider.postNextIteration());

        provider.onResume();

        assertTrue(provider.postNextIteration());
    }
}
