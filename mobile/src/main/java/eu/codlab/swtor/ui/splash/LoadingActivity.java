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
        IAppManager app_manager = getDependencyInjector().getAppManager();
        app_manager.removeListener(this);

        super.onPause();
    }

    private void checkDependency() {
        IAppManager app_manager = getDependencyInjector().getAppManager();

        if (app_manager.isInit()) {
            Log.d("LoadingActivity", "is init");
            IDatabaseProvider database = getDependencyInjector().getDatabaseProvider();
            if (database.hasLoadedDatabaseValues()) {
                Log.d("LoadingActivity", "has loaded database");
                if (database.hasValues()) {
                    Log.d("LoadingActivity", "has values");
                } else {
                    Log.d("LoadingActivity", "has no values");
                    TutorialActivity.startAndFinish(this);
                }
            }
        } else {
            Log.d("LoadingActivity", "is not init");
            app_manager.init(this, this);
        }
    }

    @Override
    public void onAppInitialized() {
        Log.d("LoadingActivity", "on app initialized");
        checkDependency();
    }
}
