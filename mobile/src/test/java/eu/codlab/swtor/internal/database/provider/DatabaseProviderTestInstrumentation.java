package eu.codlab.swtor.internal.database.provider;

import android.test.ActivityUnitTestCase;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.junit.Test;

import java.util.List;

import eu.codlab.swtor.ui.splash.LoadingActivity;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class DatabaseProviderTestInstrumentation extends ActivityUnitTestCase {//ActivityInstrumentationTestCase2<LoadingActivity> {

    public DatabaseProviderTestInstrumentation() {
        super(LoadingActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    private void init() {
        FlowManager.init(getActivity());
    }

    @Test
    public void testLoadDatabaseIntoMemory() {
        init();
        DatabaseProvider provider = new DatabaseProvider();

        assertFalse(provider.hasLoadedDatabaseValues());
        assertTrue(provider.loadDatabaseIntoMemory());

        assertTrue(provider.hasLoadedDatabaseValues());
        assertTrue(provider.loadDatabaseIntoMemory());

    }

    @Test
    public void testReloadDatabaseInMemory() {
        init();
        DatabaseProvider provider = new DatabaseProvider();

        assertTrue(provider.reloadDatabaseInMemory());
    }

    @Test
    public void testHasLoadedDatabaseValues() {
        init();
        DatabaseProvider provider = new DatabaseProvider();

        assertFalse(provider.hasLoadedDatabaseValues());
        provider.loadDatabaseIntoMemory();
        assertTrue(provider.hasLoadedDatabaseValues());
    }

    @Test
    public void testGetAllKeys() {
        init();
        DatabaseProvider provider = new DatabaseProvider();

        assertNotNull(provider.getAllKeys());

        provider.loadDatabaseIntoMemory();
        assertNotNull(provider.getAllKeys());
    }

    @Test
    public void testGetCopyKeys() {
        init();
        DatabaseProvider provider = new DatabaseProvider();

        List all = provider.getAllKeys();
        List copy = provider.getCopyKeys();

        assertNotEquals(all, copy);
        assertEquals(all.size(), copy.size());
    }
}
