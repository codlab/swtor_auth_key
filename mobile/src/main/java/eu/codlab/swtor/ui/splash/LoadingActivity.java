package eu.codlab.swtor.ui.splash;

import android.os.Bundle;
import android.util.Log;

import eu.codlab.swtor.R;
import eu.codlab.swtor.internal.app.listeners.IAppListener;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;
import eu.codlab.swtor.internal.injector.interfaces.IDatabaseProvider;
import eu.codlab.swtor.ui.abstracts.AbstractKeysActivity;
import eu.codlab.swtor.ui.tutorial.TutorialActivity;

public class LoadingActivity extends AbstractKeysActivity implements IAppListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        checkDependency();
    }

    @Override
    public void onResume() {
        super.onResume();

        checkDependency();
    }

    @Override
    public void onPause() {
        IAppManager appManager = getDependencyInjector().getAppManager();
        appManager.removeListener(this);

        super.onPause();
    }

    protected void checkDependency() {
        IAppManager appManager = getDependencyInjector().getAppManager();

        if (appManager.isInit()) {
            IDatabaseProvider database = getDependencyInjector().getDatabaseProvider();
            Log.d("CONTENT",database.hasValues() + " "+appManager.isInit());
            if (!database.hasValues()) {
                TutorialActivity.startAndFinish(this);
            }
        } else {
            Log.d("CONTENT","call init");
            appManager.init(this, this);
        }
    }

    @Override
    public void onAppInitialized() {
        checkDependency();
    }
}
