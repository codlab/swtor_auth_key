package eu.codlab.common.security;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;
import eu.codlab.common.BuildConfig;
import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.common.dependency.listeners.IWeb;
import eu.codlab.common.security.events.CodeInvalidateEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class TimeProviderTest {

    @Before
    public void setUp() {
        DependencyInjector injector = new DependencyInjector() {
            @Override
            public void init(@NonNull Context context) {

            }

            @NonNull
            @Override
            public IWeb getNetworkTimeWebsevice() {
                return null;
            }

            @NonNull
            @Override
            public SharedPreferences getDefaultSharedPreferences() {
                return null;
            }

            @NonNull
            @Override
            public IDatabaseProvider getDatabaseProvider() {
                return null;
            }

            @NonNull
            @Override
            public EventBus getDefaultEventBus() {
                return EventBus.getDefault();
            }

            @NonNull
            @Override
            public IAppManager getAppManager() {
                return null;
            }

            @Nullable
            @Override
            public CodeProvider getCodeProvider(String provider) {
                return null;
            }

            @NonNull
            @Override
            public TimeProvider getTimeProvider() {
                return new TimeProvider();
            }
        };

        Activity activity = Robolectric.buildActivity(Activity.class).get();
        DependencyInjectorFactory.init(activity, injector);
    }

    @After
    public void flush() {
        DependencyInjectorFactory.flush();
    }

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
    public void testOnResume() throws NoSuchFieldException, IllegalAccessException {
        TimeProvider provider = new TimeProvider();

        provider.onPause();

        assertFalse(provider.isResumed());
        Field field = provider.getClass().getDeclaredField("mHandler");
        field.setAccessible(true);
        assertNull(field.get(provider));

        provider.onResume();

        assertTrue(provider.isResumed());
        field = provider.getClass().getDeclaredField("mHandler");
        field.setAccessible(true);
        assertNotNull(field.get(provider));
    }

    @Test
    public void testOnPause() throws NoSuchFieldException, IllegalAccessException {
        TimeProvider provider = new TimeProvider();

        provider.onPause();

        assertTrue(provider.isPaused());
        Field field = provider.getClass().getDeclaredField("mHandler");
        field.setAccessible(true);
        assertNull(field.get(provider));

        provider.onResume();

        assertFalse(provider.isPaused());
        field = provider.getClass().getDeclaredField("mHandler");
        field.setAccessible(true);
        assertNotNull(field.get(provider));
    }
}
