package eu.codlab.swtor.internal.injector;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 06/02/16.
 */
public class DependencyInjectorFactoryTest {

    @Test
    public void testConstructor() {
        Class<DependencyInjectorFactory> clazz = DependencyInjectorFactory.class;

        assertEquals(1, clazz.getConstructors().length);

        Constructor<?> constructor = null;
        try {
            constructor = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        assertNotNull(constructor);
        assertFalse(constructor.isAccessible());
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
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
