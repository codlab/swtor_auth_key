package eu.codlab.swtor.internal.injector;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;

import eu.codlab.swtor.TestUtil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class DependencyInjectorFactoryTest {

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<DependencyInjectorFactory> clazz = DependencyInjectorFactory.class;

        TestUtil.assertUtilityClassWellDefined(clazz);
    }

    @Test
    public void testInstantiation() {

        boolean ok = true;

        try {
            DependencyInjector injector = DependencyInjectorFactory.getDependencyInjector();
        } catch (Exception exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testInstantationOk() {
        boolean ok = true;

        Context context = Mockito.mock(Context.class);
        DependencyInjector injector = new DependencyStandardInjector();
        DependencyInjector injector2 = null;

        DependencyInjectorFactory.init(context, injector);

        try {

            injector2 = DependencyInjectorFactory.getDependencyInjector();
        } catch (Exception exception) {
            ok = false;
        }

        assertTrue(ok);
        assertEquals(injector, injector2);
    }
}
