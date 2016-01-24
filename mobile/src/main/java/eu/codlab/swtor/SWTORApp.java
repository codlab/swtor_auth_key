package eu.codlab.swtor;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import eu.codlab.swtor.injector.DependencyInjectorFactory;
import eu.codlab.swtor.injector.DependencyStandardInjector;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class SWTORApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FlowManager.init(this);

        DependencyInjectorFactory.init(this, DependencyStandardInjector.class);
    }

}
