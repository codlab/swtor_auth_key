package eu.codlab.swtor.utils;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.test.TestUtil;

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
        assertNotNull(Constants.BACKGROUND);
        assertNotNull(Constants.DESCRIPTION);
        assertNotNull(Constants.TITLE);
        assertNotNull(Constants.RES);
    }
}
