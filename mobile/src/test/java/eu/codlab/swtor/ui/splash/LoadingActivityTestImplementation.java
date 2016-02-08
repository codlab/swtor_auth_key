package eu.codlab.swtor.ui.splash;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class LoadingActivityTestImplementation extends ActivityInstrumentationTestCase2<LoadingActivity> {

    public LoadingActivityTestImplementation(){
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
    public void testCheckDependencyAppManagerNotInit() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        IAppManager appManager = getActivity().getDependencyInjector().getAppManager();

        //set checkDependency as invokable
        Method checkDependency = getActivity().getClass().getDeclaredMethod("checkDependecy");
        checkDependency.setAccessible(true);


        assertNotNull(appManager);

        assertFalse(appManager.isInit());

        assertNotNull(checkDependency);

        checkDependency.invoke(getActivity());

        //test that after checkDependecy(), the app manager is then init
        assertTrue(appManager.isInit());
    }
}
