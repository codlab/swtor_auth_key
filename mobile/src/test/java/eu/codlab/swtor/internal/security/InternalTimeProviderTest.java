package eu.codlab.swtor.internal.security;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 07/02/16.
 */
public class InternalTimeProviderTest {

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        TestUtil.assertUtilityClassWellDefined(InternalTimeProvider.class);
    }

    @Test
    public void testInternalMethod() {
        DependencyInjector injector = DependencyInjectorFactory.getDependencyInjector();
        CodeInvalidateEvent event = new CodeInvalidateEvent(0);

        CodeInvalidateEvent result = InternalTimeProvider.postEvent(injector, event);

        assertEquals(event, result);
    }

    @Test
    public void testInternalRunPostedNextIteration() {
        TimeProvider provider = new TimeProvider();
        provider.onResume();

        boolean result = InternalTimeProvider.internalRunPostedNextIteration(provider);

        assertTrue(result);

    }
}
