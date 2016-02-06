package eu.codlab.swtor;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class TestUtilTest {
    @Test
    public void testUtilImplementation() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(TestUtil.class);
    }
}
