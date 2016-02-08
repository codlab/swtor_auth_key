package eu.codlab.swtor.internal.database.provider;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.TestUtil;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class DatabaseProviderTest {

    private void init() {
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        init();
        TestUtil.assertPublicClassWellDefined(DatabaseProvider.class);
    }

    @Test
    public void testHasValues() {
        init();
        DatabaseProvider provider = new DatabaseProvider();

        assertNotEquals(0, provider.getAllKeys());
        assertFalse(provider.hasValues());
    }
}
