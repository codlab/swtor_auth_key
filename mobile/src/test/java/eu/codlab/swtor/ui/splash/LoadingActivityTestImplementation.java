package eu.codlab.swtor.ui.splash;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.internal.app.listeners.IAppListener;
import eu.codlab.swtor.internal.app.provider.AppManager;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;
import eu.codlab.test.TestUtil;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class LoadingActivityTestImplementation {


    private LoadingActivity mActivity;
    private ShadowActivity mShadowActivity;

    @Before
    public void setUp() throws Exception {

        DependencyInjector injector = new DependencyStandardInjector() {
            private AppManager mAppManager;

            @NonNull
            @Override
            public IAppManager getAppManager() {
                if (mAppManager == null)
                    mAppManager = new AppManager();
                return mAppManager;
            }
        };

        DependencyInjectorFactory.init(mActivity, injector);

        mActivity = Robolectric.setupActivity(LoadingActivity.class);
        mShadowActivity = (ShadowActivity) ShadowExtractor.extract(mActivity);

    }

    @After
    public void after() {
        FlowManager.destroy();
    }

    @Test
    public void testOnCreate() {
        assertNotNull(mActivity.getDependencyInjector());
    }

    @Test
    public void testCheckDependencyAppManagerNotInit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //force new AppManager
        DependencyInjectorFactory.flush();
        DependencyInjector injector = new DependencyStandardInjector() {
            private AppManager mAppManager;

            @NonNull
            @Override
            public IAppManager getAppManager() {
                if (mAppManager == null)
                    mAppManager = new AppManager();
                return mAppManager;
            }
        };

        DependencyInjectorFactory.init(mActivity, injector);


        //create the activity
        LoadingActivity activity = Robolectric.buildActivity(LoadingActivity.class).create().resume().get();
        ShadowActivity shadowActivity = (ShadowActivity) ShadowExtractor.extract(activity);
        TestUtil.purgeShadowActivityStartActivity(shadowActivity);

        IAppManager appManager = activity.getDependencyInjector().getAppManager();


        assertNotNull(appManager);

        //checkDependency is called in onCreate -> .create()

        long time = System.currentTimeMillis();
        long timeout = time + 5000;
        while (time < timeout && !appManager.isInit()) {
            time = System.currentTimeMillis();
        }

        //test that after checkDependecy(), the app manager is then init
        assertTrue(appManager.isInit());
        assertFalse(activity.isFinishing());


        //test that when having a key in the database, we do not start the activity
        TestUtil.purgeShadowActivityStartActivity(shadowActivity);
        DependencyInjectorFactory.getDependencyInjector().getDatabaseProvider()
                .getAllKeys().add(new Key());
        activity.checkDependency();

        assertTrue(activity.getDependencyInjector().getDatabaseProvider().hasLoadedDatabaseValues());
        assertTrue(activity.getDependencyInjector().getDatabaseProvider().hasValues());

        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertNotNull(startedIntent);

        //erase every key to make sure we have none
        TestUtil.purgeShadowActivityStartActivity(shadowActivity);
        DependencyInjectorFactory.getDependencyInjector().getDatabaseProvider()
                .getAllKeys().clear();


        //next time, test we are launching activity and quitting
        activity.checkDependency();

        assertTrue(activity.getDependencyInjector().getDatabaseProvider().hasLoadedDatabaseValues());
        assertFalse(activity.getDependencyInjector().getDatabaseProvider().hasValues());
        startedIntent = shadowActivity.getNextStartedActivity();

        assertNotNull(startedIntent);
        assertThat(startedIntent.getComponent().getClassName(),
                equalTo(TutorialActivity.class.getName()));

        assertTrue(activity.isFinishing());
    }


    @Test
    public void testOnPause() throws NoSuchFieldException, IllegalAccessException {
        LoadingActivity activity = Robolectric.buildActivity(LoadingActivity.class).create().get();

        activity.onPause();

        AppManager manager = (AppManager) activity.getDependencyInjector().getAppManager();
        Field field = manager.getClass().getDeclaredField("mAppListeners");
        assertNotNull(field);
        field.setAccessible(true);

        List<IAppListener> listeners = (List<IAppListener>) field.get(manager);

        assertNotNull(listeners);

        for (IAppListener listener : listeners) {
            assertNotEquals(activity, listener);
        }
    }
}
