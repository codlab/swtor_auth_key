package eu.codlab.common.security;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.test.TestUtil;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class SecurityConstantsTest {

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(SecurityConstants.class);
    }
}
