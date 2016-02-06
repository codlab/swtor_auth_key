package eu.codlab.swtor.ui.splash;

import android.os.Bundle;

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

    private void checkDependency() {
        IAppManager appManager = getDependencyInjector().getAppManager();

        if (appManager.isInit()) {
            IDatabaseProvider database = getDependencyInjector().getDatabaseProvider();
            if (database.hasLoadedDatabaseValues()
                    && !database.hasLoadedDatabaseValues()) {
                TutorialActivity.startAndFinish(this);
            }
        } else {
            appManager.init(this, this);
        }
    }

    @Override
    public void onAppInitialized() {
        checkDependency();
    }
}
