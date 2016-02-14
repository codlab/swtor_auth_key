package eu.codlab.swtor.internal.app.impl;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.crashlytics_wear.CrashlyticsWearApplication;
import eu.codlab.swtor.internal.injector.DependencyWearInjector;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class SwtorApplication extends CrashlyticsWearApplication {

    /**
     * Simple application creation
     * <p/>
     * We create the default dependency injector in it
     */
    @Override
    public void onCreate() {
        super.onCreate();

        DependencyInjectorFactory.init(this, new DependencyWearInjector());
    }
}
