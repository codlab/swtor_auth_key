package eu.codlab.swtor.internal.injector;

import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IWeb;
import eu.codlab.common.network.NetworkConstants;
import eu.codlab.common.network.ToStringConverterFactory;
import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.ui.splash.LoadingActivity;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kevinleperf on 09/02/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(sdk = 21, constants = BuildConfig.class)
public class DependencyInjectorStandardTest {

    private DependencyStandardInjector mInjector;
    private LoadingActivity mActivity;

    @Before
    public void setUp() {
        DependencyInjectorFactory.flush();
        mActivity = Robolectric.buildActivity(LoadingActivity.class).get();
        mInjector = new DependencyStandardInjector();
        mInjector.init(mActivity);

        DependencyInjectorFactory.init(mActivity, mInjector);
    }

    @After
    public void tearDown() {
        DependencyInjectorFactory.flush();
    }

    @Test
    public void testGetAppManager() {
        IAppManager appManager = mInjector.getAppManager();

        IAppManager manager = DependencyInjectorFactory.getDependencyInjector()
                .getAppManager();

        assertNotNull(manager);
        assertEquals(appManager, manager);
    }


    @Test
    public void testgetNetworkTimeInjector() {
        Retrofit retrofit = mInjector.getRetrofit();
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
        IWeb iweb = mInjector.getNetworkTimeWebsevice();

        assertNotNull(iweb);

        assertNotNull(iweb.getRoot());
    }

    @Test
    public void testGetSharedPreferences() {

        SharedPreferences sharedPreferences = DependencyInjectorFactory
                .getDependencyInjector()
                .getDefaultSharedPreferences();

        assertNotNull(sharedPreferences);
        assertEquals(mInjector.getDefaultSharedPreferences(), sharedPreferences);
    }
}
