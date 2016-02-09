package eu.codlab.swtor.internal.database.provider;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        TestUtil.cleanDBFlow();
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
}
