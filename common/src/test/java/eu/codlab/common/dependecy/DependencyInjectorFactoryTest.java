package eu.codlab.common.dependecy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.InvocationTargetException;

import de.greenrobot.event.EventBus;
import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.common.dependency.listeners.IWeb;
import eu.codlab.common.security.CodeProvider;
import eu.codlab.common.security.TimeProvider;
import eu.codlab.test.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 06/02/16.
 */
@RunWith(RobolectricTestRunner.class)
public class DependencyInjectorFactoryTest {

    private Activity mActivity;
    private DependencyInjector mDefaultInjector;

    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(Activity.class).get();
        mDefaultInjector = createDependencyInjector();
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<DependencyInjectorFactory> clazz = DependencyInjectorFactory.class;

        TestUtil.assertUtilityClassWellDefined(clazz);
    }

    @Test
    public void testFlush() {
        DependencyInjectorFactory.init(mActivity, mDefaultInjector);
        assertNotNull(DependencyInjectorFactory.getDependencyInjector());

        DependencyInjectorFactory.flush();

        boolean ok = true;
        try {
            DependencyInjector injector = DependencyInjectorFactory.getDependencyInjector();
        } catch (Exception exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testInstantiation() {

        boolean ok = true;

        DependencyInjectorFactory.flush();

        try {
            DependencyInjector injector = DependencyInjectorFactory.getDependencyInjector();
        } catch (Exception exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testInstantationOk() {
        boolean ok = true;

        DependencyInjector injector2 = null;

        DependencyInjectorFactory.init(mActivity, mDefaultInjector);

        try {

            injector2 = DependencyInjectorFactory.getDependencyInjector();
        } catch (Exception exception) {
            ok = false;
        }

        assertTrue(ok);
        assertEquals(mDefaultInjector, injector2);
    }

    private DependencyInjector createDependencyInjector() {
        return new DependencyInjector() {
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
                return null;
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
                return null;
            }
        };
    }
}
