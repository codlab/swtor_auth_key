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

    protected DependencyInjector getDependencyInjector() {
        return mDependencyInjector;
    }


}
