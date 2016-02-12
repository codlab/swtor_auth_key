package eu.codlab.swtor.internal.database.impl;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.test.TestUtil;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class DatabaseTest {
    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(Database.class);
    }
}
