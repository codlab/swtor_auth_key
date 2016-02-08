package eu.codlab.swtor.internal.app.provider;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import eu.codlab.swtor.internal.app.listeners.IAppListener;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class AppManagerTest {

    @Test
    public void testHasListeners() {
        AppManager manager = new AppManager();

        IAppListener listener = new IAppListener() {
            @Override
            public void onAppInitialized() {

            }
        };

        assertFalse(manager.hasListeners());

        manager.init(Mockito.mock(Context.class), listener);

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
                warned[01] = true;
            }
        });

        long time = System.currentTimeMillis();
        long timeout = time + 5000;

        while (time < timeout && !manager.isInit()) {
            time = System.currentTimeMillis();
        }

        //verify listener not called
        assertFalse(warned[0]);
        //but legitimate was
        assertTrue(warned[1]);

    }
}
