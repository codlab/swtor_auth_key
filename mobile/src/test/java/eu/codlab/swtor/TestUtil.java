package eu.codlab.swtor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by kevinleperf on 06/02/16.
 */
public final class TestUtil {

    public static void assertArrayValid(final Class<?> clazz,
                                        String name,
                                        Class<?> type,
                                        boolean priv,
                                        boolean stat) throws NoSuchFieldException {
        Field field = clazz.getDeclaredField(name);

        int modifier = field.getModifiers();

        assertNotNull(field);
        assertEquals(type, field.getType());
        assertEquals(priv, Modifier.isPrivate(modifier));
        assertEquals(stat, Modifier.isStatic(modifier));
    }

    public static void assertPublicClassWellDefined(final Class<?> clazz)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        assertNotEquals("There must be at least one constructor", 0,
                clazz.getConstructors().length);

        final Constructor<?> constructor = clazz.getDeclaredConstructor();

        if (Modifier.isPrivate(constructor.getModifiers())) {
            fail("constructor is private");
        }

        assertNotNull(constructor.newInstance());
    }

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

    private TestUtil() {

    }
}
