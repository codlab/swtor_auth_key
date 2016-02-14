package eu.codlab.common.security;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.common.network.NetworkConstants;
import eu.codlab.test.TestUtil;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class NetworkConstantsTest {
    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(NetworkConstants.class);
    }
}
