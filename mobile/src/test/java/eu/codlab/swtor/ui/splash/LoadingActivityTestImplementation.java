package eu.codlab.swtor.ui.splash;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class LoadingActivityTestImplementation {


    private LoadingActivity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(LoadingActivity.class);

        DependencyInjectorFactory.init(mActivity, new DependencyStandardInjector());
    }

    @Test
    public void testOnCreate() {
        assertNotNull(mActivity.getDependencyInjector());
    }

    @Test
    public void testCheckDependencyAppManagerNotInit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        IAppManager appManager = mActivity.getDependencyInjector().getAppManager();

        //set checkDependency as invokable


        assertNotNull(appManager);

        assertFalse(appManager.isInit());

        mActivity.checkDependency();

        long time = System.currentTimeMillis();
        long timeout = time + 5000;
        while(time < timeout && !appManager.isInit()    ){
            time = System.currentTimeMillis();
        }

        //test that after checkDependecy(), the app manager is then init
        assertTrue(appManager.isInit());
    }
}
