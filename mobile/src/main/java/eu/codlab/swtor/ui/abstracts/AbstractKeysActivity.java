package eu.codlab.swtor.ui.abstracts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;

/**
 * Created by kevinleperf on 24/01/16.
 */
public abstract class AbstractKeysActivity extends AppCompatActivity {

    private DependencyInjector _dependency_injector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _dependency_injector = DependencyInjectorFactory.getDependencyInjector();
    }

    @Override
    public void onResume() {
        super.onResume();

        /*_dependency_injector.getDefaultEventBus().register(this);

        _dependency_injector
                .getDatabaseProvider()
                .loadDatabaseIntoMemory();*/
    }

    @Override
    public void onPause() {
        /*_dependency_injector.getDefaultEventBus().unregister(this);*/

        super.onPause();
    }

    protected DependencyInjector getDependencyInjector() {
        return _dependency_injector;
    }


}
