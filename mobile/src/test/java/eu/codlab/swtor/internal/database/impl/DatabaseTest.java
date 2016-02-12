package eu.codlab.swtor.internal.database.impl;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.internal.database.provider.DatabaseProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class DatabaseTest {
    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(Database.class);
    }

    @Test
    public void testGetLastKey() {
        DatabaseProvider provider = new DatabaseProvider();

        assertEquals(0, provider.getAllKeys().size());
        assertNull(provider.getLastKey());
    }
}
