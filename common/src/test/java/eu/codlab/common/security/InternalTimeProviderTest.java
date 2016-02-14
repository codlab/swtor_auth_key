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

import java.lang.reflect.InvocationTargetException;

import de.greenrobot.event.EventBus;
import eu.codlab.common.BuildConfig;
import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.common.dependency.listeners.IWeb;
import eu.codlab.common.security.events.CodeInvalidateEvent;
import eu.codlab.test.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class InternalTimeProviderTest {

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
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(InternalTimeProvider.class);
    }

    @Test
    public void testInternalMethod() {
        DependencyInjector injector = DependencyInjectorFactory.getDependencyInjector();
        CodeInvalidateEvent event = new CodeInvalidateEvent(0);

        CodeInvalidateEvent result = InternalTimeProvider.postEvent(injector, event);

        assertEquals(event, result);
    }

    @Test
    public void testInternalRunPostedNextIteration() {
        TimeProvider provider = new TimeProvider();
        provider.onResume();

        boolean result = InternalTimeProvider.internalRunPostedNextIteration(provider);

        assertTrue(result);

    }
}
