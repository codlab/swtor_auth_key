package eu.codlab.swtor.internal.injector;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import eu.codlab.swtor.TestUtil;
import eu.codlab.swtor.internal.network.IWeb;
import eu.codlab.swtor.internal.network.NetworkConstants;
import eu.codlab.swtor.internal.network.ToStringConverterFactory;
import eu.codlab.swtor.ui.splash.LoadingActivity;
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
@RunWith(RobolectricTestRunner.class)
public class DependencyInjectorFactoryTest {

    private LoadingActivity mActivity;

    @Before
    public void setUp() {
        mActivity = Robolectric.buildActivity(LoadingActivity.class).get();
    }

    @Test
    public void testConstructor() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<DependencyInjectorFactory> clazz = DependencyInjectorFactory.class;

        TestUtil.assertUtilityClassWellDefined(clazz);
    }

    @Test
    public void testFlush() {
        DependencyInjectorFactory.init(mActivity, new DependencyStandardInjector());
        assertNotNull(DependencyInjectorFactory.getDependencyInjector());

        DependencyInjectorFactory.flush();

        boolean ok = true;
        try {
            DependencyInjector injector = DependencyInjectorFactory.getDependencyInjector();
        } catch (Exception exception) {
            ok = false;
        }

        assertFalse(ok);
    }

    @Test
    public void testInstantiation() {

        boolean ok = true;

        DependencyInjectorFactory.flush();

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

        DependencyInjector injector = new DependencyStandardInjector();
        DependencyInjector injector2 = null;

        DependencyInjectorFactory.init(mActivity, injector);

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
        injector.init(mActivity);

        Retrofit retrofit = injector.getRetrofit();
        assertNotNull(retrofit);
        assertEquals(NetworkConstants.SWTOR, retrofit.baseUrl().url().url().toString());

        List<Converter.Factory> converters = retrofit.converterFactories();
        assertNotNull(converters);
        assertNotEquals(0, converters.size());

        boolean found = false;
        for (Converter.Factory factory : converters) {
            if (factory instanceof ToStringConverterFactory)
                found = true;
        }

        assertTrue(found);
    }

    @Test
    public void testIWeb() {
        DependencyStandardInjector injector = new DependencyStandardInjector();
        Context context = Mockito.mock(Context.class);
        injector.init(context);

        IWeb iweb = injector.getNetworkTimeWebsevice();

        assertNotNull(iweb);

        assertNotNull(iweb.getRoot());
    }

    @Test
    public void testGetSharedPreferences() {
        DependencyInjector injector = new DependencyStandardInjector();

        DependencyInjectorFactory.flush();
        DependencyInjectorFactory.init(mActivity, injector);

        SharedPreferences sharedPreferences = DependencyInjectorFactory
                .getDependencyInjector()
                .getDefaultSharedPreferences();

        assertNotNull(sharedPreferences);
        assertEquals(injector.getDefaultSharedPreferences(), sharedPreferences);
    }
}
