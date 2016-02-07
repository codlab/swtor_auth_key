package eu.codlab.swtor.utils;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.TestUtil;

import static org.junit.Assert.assertNotNull;

/**
 * Created by kevinleperf on 07/02/16.
 */
public class ConstantsTest {

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(Constants.class);
    }

    @Test
    public void testArrays() {
        assertNotNull(Constants.background);
        assertNotNull(Constants.description);
        assertNotNull(Constants.title);
        assertNotNull(Constants.res);
    }
}
