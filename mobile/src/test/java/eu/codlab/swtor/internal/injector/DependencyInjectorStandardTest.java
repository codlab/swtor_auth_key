package eu.codlab.swtor.internal.injector;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import eu.codlab.swtor.internal.injector.interfaces.IAppManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kevinleperf on 09/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DependencyInjectorStandardTest {

    @Test
    public void testGetAppManager() {

        DependencyInjector injector = new DependencyStandardInjector();

        DependencyInjectorFactory.init(Mockito.mock(Context.class), injector);
        IAppManager appManager = injector.getAppManager();

        IAppManager manager = DependencyInjectorFactory.getDependencyInjector()
                .getAppManager();

        assertNotNull(manager);
        assertEquals(appManager, manager);
    }
}
