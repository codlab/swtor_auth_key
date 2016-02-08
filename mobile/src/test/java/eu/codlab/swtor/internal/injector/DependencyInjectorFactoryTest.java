package eu.codlab.swtor.internal.injector;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.internal.network.IWeb;
import eu.codlab.swtor.internal.network.NetworkConstants;
import eu.codlab.swtor.internal.network.ToStringConverterFactory;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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

    @Test
    public void testgetNetworkTimeInjector() {
        DependencyStandardInjector injector = new DependencyStandardInjector();
        Context context = Mockito.mock(Context.class);
        injector.init(context);

        Retrofit retrofit = injector.getRetrofit();
        assertNotNull(retrofit);
        assertEquals(NetworkConstants.SWTOR, retrofit.baseUrl().url().url().toString());

        List<Converter.Factory> converters = retrofit.converterFactories();
        assertNotNull(converters);
        assertNotEquals(0, converters.size());

        boolean found = false;
        for(Converter.Factory factory : converters){
            if(factory instanceof ToStringConverterFactory)
                found = true;
        }

        assertTrue(found);
    }

    @Test
    public void testIWeb(){
        DependencyStandardInjector injector = new DependencyStandardInjector();
        Context context = Mockito.mock(Context.class);
        injector.init(context);

        IWeb iweb = injector.getNetworkTimeWebsevice();

        assertNotNull(iweb);

        assertNotNull(iweb.getRoot());
    }
}
