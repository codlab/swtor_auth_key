package eu.codlab.swtor.internal.security;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.internal.network.NetworkConstants;

/**
 * Created by kevinleperf on 08/02/16.
 */
public class NetworkConstantsTest {
    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(NetworkConstants.class);
    }
}
