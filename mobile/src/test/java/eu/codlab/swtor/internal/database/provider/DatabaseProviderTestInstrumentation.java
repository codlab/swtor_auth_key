package eu.codlab.swtor.internal.database.provider;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 08/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DatabaseProviderTestInstrumentation {


    @Before
    public void init() {
        FlowManager.init(Robolectric.setupActivity(TutorialActivity.class));
    }

    @After
    public void tearDown() throws Exception {
        TestUtil.cleanDBFlow();
    }

    @Test
    public void testLoadDatabaseIntoMemory() {
        DatabaseProvider provider = new DatabaseProvider();

        assertFalse(provider.hasLoadedDatabaseValues());
        assertTrue(provider.loadDatabaseIntoMemory());

        assertTrue(provider.hasLoadedDatabaseValues());
        assertTrue(provider.loadDatabaseIntoMemory());

    }

    @Test
    public void testReloadDatabaseInMemory() {
        DatabaseProvider provider = new DatabaseProvider();

        assertTrue(provider.reloadDatabaseInMemory());
    }

    @Test
    public void testHasLoadedDatabaseValues() {
        DatabaseProvider provider = new DatabaseProvider();

        assertFalse(provider.hasLoadedDatabaseValues());
        provider.loadDatabaseIntoMemory();
        assertTrue(provider.hasLoadedDatabaseValues());
    }

    @Test
    public void testGetAllKeys() {
        DatabaseProvider provider = new DatabaseProvider();

        assertNotNull(provider.getAllKeys());

        provider.loadDatabaseIntoMemory();
        assertNotNull(provider.getAllKeys());
    }

    @Test
    public void testGetCopyKeys() {
        DatabaseProvider provider = new DatabaseProvider();

        List all = provider.getAllKeys();
        List copy = provider.getCopyKeys();

        assertEquals(all.size(), copy.size());

        //to test the fact the two list are different, we had an item into one
        all.add(null);

        assertNotEquals(all.toString(), copy.toString());
    }
}
