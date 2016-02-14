package eu.codlab.swtor.ui.abstracts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.wearable.activity.WearableActivity;

import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;

/**
 * Created by kevinleperf on 24/01/16.
 */
public abstract class AbstractKeysActivity extends WearableActivity {

    private DependencyInjector mDependencyInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDependencyInjector = DependencyInjectorFactory.getDependencyInjector();
    }

    public DependencyInjector getDependencyInjector() {
        return mDependencyInjector;
    }


}
