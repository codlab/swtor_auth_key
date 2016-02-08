package eu.codlab.swtor.ui.splash;

import android.test.ActivityUnitTestCase;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class LoadingActivityTestImplementation extends ActivityUnitTestCase {

    public LoadingActivityTestImplementation() {
        this(LoadingActivity.class);
    }

    public LoadingActivityTestImplementation(Class<LoadingActivity> activityClass) {
        super(activityClass);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        DependencyInjectorFactory.init(getActivity(), new DependencyStandardInjector());
    }

    @Test
    public void testOnCreate(){
        LoadingActivity activity = (LoadingActivity) getActivity();

        assertNull(activity.getDependencyInjector());

        activity.onCreate(null);

        assertNotNull(activity.getDependencyInjector());
    }

    @Test
    public void testCheckDependencyAppManagerNotInit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        LoadingActivity activity = (LoadingActivity) getActivity();

        IAppManager appManager = activity.getDependencyInjector().getAppManager();

        //set checkDependency as invokable


        assertNotNull(appManager);

        assertFalse(appManager.isInit());

        activity.checkDependency();

        //test that after checkDependecy(), the app manager is then init
        assertTrue(appManager.isInit());
    }
}
