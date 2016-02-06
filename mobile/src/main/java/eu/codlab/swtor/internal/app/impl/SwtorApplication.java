package eu.codlab.swtor.internal.app.impl;

import android.app.Application;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.DependencyStandardInjector;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class SwtorApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DependencyInjectorFactory.init(this, new DependencyStandardInjector());
    }

}
