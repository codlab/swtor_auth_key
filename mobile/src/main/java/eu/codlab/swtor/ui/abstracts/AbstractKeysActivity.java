package eu.codlab.swtor.ui.abstracts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;

/**
 * Created by kevinleperf on 24/01/16.
 */
public abstract class AbstractKeysActivity extends AppCompatActivity {

    private DependencyInjector mDependencyInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDependencyInjector = DependencyInjectorFactory.getDependencyInjector();
    }

    @Override
    public void onResume() {
        super.onResume();

        /*mDependencyInjector.getDefaultEventBus().register(this);

        mDependencyInjector
                .getDatabaseProvider()
                .loadDatabaseIntoMemory();*/
    }

    @Override
    public void onPause() {
        /*mDependencyInjector.getDefaultEventBus().unregister(this);*/

        super.onPause();
    }

    protected DependencyInjector getDependencyInjector() {
        return mDependencyInjector;
    }


}
