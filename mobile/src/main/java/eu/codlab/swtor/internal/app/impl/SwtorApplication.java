package eu.codlab.swtor.internal.app.impl;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class SwtorApplication extends Application {

    /**
     * Simple application creation
     * <p/>
     * We create the default dependency injector in it
     */
    @Override
    public void onCreate() {
        super.onCreate();

        DependencyInjectorFactory.init(this, new DependencyStandardInjector());
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        try {
            MultiDex.install(this);
        } catch (Exception exception) {

        }
    }

}
