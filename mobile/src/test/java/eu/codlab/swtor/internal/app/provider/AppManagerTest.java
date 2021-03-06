package eu.codlab.swtor.internal.app.provider;

import android.content.Context;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IAppListener;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class AppManagerTest {

    @Before
    public void setUp() throws Exception {
        DependencyInjectorFactory.init(Mockito.mock(Context.class), new DependencyStandardInjector());
    }

    @After
    public void after() {
        FlowManager.destroy();
    }

    @Test
    public void testHasListeners() {
        AppManager manager = new AppManager();

        IAppListener listener = new IAppListener() {
            @Override
            public void onAppInitialized() {

            }
        };

        assertFalse(manager.hasListeners());

        manager.appendListener(listener);

        assertTrue(manager.hasListeners());
    }


    @Test
    public void testRemoveListeners() {
        AppManager manager = new AppManager();

        final boolean[] warned = {false, false};
        IAppListener listener = new IAppListener() {
            @Override
            public void onAppInitialized() {
                warned[0] = true;
            }
        };

        manager.appendListener(listener);

        manager.removeListener(listener);

        manager.init(Mockito.mock(Context.class), new IAppListener() {
            @Override
            public void onAppInitialized() {
                warned[1] = true;
            }
        });

        long time = System.currentTimeMillis();
        long timeout = time + 2000;

        while (time < timeout && !warned[1]) {
            time = System.currentTimeMillis();
        }

        assertTrue(manager.isInit());

        //verify listener not called
        assertFalse(warned[0]);
        //but legitimate was
        assertTrue(warned[1]);

    }


}
