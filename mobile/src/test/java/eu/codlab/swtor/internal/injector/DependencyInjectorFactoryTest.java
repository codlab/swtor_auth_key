package eu.codlab.swtor.internal.injector;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class DependencyInjectorFactoryTest {

    public static void assertUtilityClassWellDefined(final Class<?> clazz)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        assertTrue("class must be final",
                Modifier.isFinal(clazz.getModifiers()));
        assertEquals("There must be only one constructor", 1,
                clazz.getDeclaredConstructors().length);
        final Constructor<?> constructor = clazz.getDeclaredConstructor();
        if (constructor.isAccessible() ||
                !Modifier.isPrivate(constructor.getModifiers())) {
            fail("constructor is not private");
        }
        constructor.setAccessible(true);
        constructor.newInstance();
        constructor.setAccessible(false);
        for (final Method method : clazz.getMethods()) {
            if (!Modifier.isStatic(method.getModifiers())
                    && method.getDeclaringClass().equals(clazz)) {
                fail("there exists a non-static method:" + method);
            }
        }
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<DependencyInjectorFactory> clazz = DependencyInjectorFactory.class;

        assertUtilityClassWellDefined(clazz);
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
