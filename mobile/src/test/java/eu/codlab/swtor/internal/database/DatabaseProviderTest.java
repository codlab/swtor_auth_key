package eu.codlab.swtor.internal.database;

import com.raizlabs.android.dbflow.config.FlowManager;

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
import java.util.ArrayList;
import java.util.Comparator;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.database.provider.DatabaseProvider;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;
import eu.codlab.test.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DatabaseProviderTest {


    @Before
    public void setUp() throws Exception {
        TutorialActivity activity = Robolectric.setupActivity(TutorialActivity.class);

        DependencyInjectorFactory.init(activity, new DependencyStandardInjector());
        FlowManager.init(activity);
    }

    @After
    public void after() {
        FlowManager.destroy();
        DependencyInjectorFactory.flush();
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertPublicClassWellDefined(DatabaseProvider.class);
    }

    @Test
    public void testHasValues() {
        DatabaseProvider provider = new DatabaseProvider();

        assertFalse(provider.hasLoadedDatabaseValues());
        assertEquals(0, provider.getAllKeys().size());
        assertFalse(provider.hasValues());

        provider.loadDatabaseIntoMemory();

        assertTrue(provider.hasLoadedDatabaseValues());
        assertEquals(0, provider.getAllKeys().size());
        assertFalse(provider.hasValues());

        provider.getAllKeys().add(new Key());
        assertTrue(provider.hasValues());
    }

    @Test
    public void testGetLastKeyWithProvided() throws NoSuchFieldException, IllegalAccessException {
        DatabaseProvider provider = new DatabaseProvider();

        Field field = provider.getClass().getDeclaredField("mDatabaseValue");
        field.setAccessible(true);

        field.set(provider, new ArrayList<>());

        Key key = new Key();

        provider.updateKey(key);

        assertEquals(key, provider.getLastKey());
        assertEquals(key, provider.getAllKeys().get(0));
    }

    @Test
    public void testWarnListeners() throws NoSuchFieldException, IllegalAccessException {
        DatabaseProvider provider = new DatabaseProvider();

        Field field = provider.getClass().getDeclaredField("mDatabaseValue");
        field.setAccessible(true);

        field.set(provider, new ArrayList<>());

        Key key = new Key();

        provider.updateKey(key);

        provider.warnListeners();

        DatabaseEvent event = DependencyInjectorFactory.getDependencyInjector()
                .getDefaultEventBus().getStickyEvent(DatabaseEvent.class);

        assertNotNull(event);
        assertEquals(key, event.getKey());
    }

    @Test
    public void testComparator() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        DatabaseProvider provider = new DatabaseProvider();
        Method method = provider.getClass().getDeclaredMethod("getComparator");
        method.setAccessible(true);

        Comparator<Key> comparator = (Comparator<Key>) method.invoke(provider);

        Key keyLeft = new Key();
        keyLeft.setUpdatedAt(0);

        Key keyRight = new Key();
        keyRight.setUpdatedAt(1);

        assertEquals(1, comparator.compare(keyLeft, keyRight));

        keyLeft.setUpdatedAt(1);
        assertEquals(0, comparator.compare(keyLeft, keyRight));

        keyRight.setUpdatedAt(0);
        assertEquals(-1, comparator.compare(keyLeft, keyRight));
    }
}
