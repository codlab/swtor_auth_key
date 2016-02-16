package eu.codlab.swtor.ui.splash;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import eu.codlab.common.dependency.listeners.IAppListener;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.swtor.R;
import eu.codlab.swtor.ui.abstracts.AbstractKeysActivity;
import eu.codlab.swtor.ui.main.ShowCodeActivity;

public class LoadingActivity extends AbstractKeysActivity implements IAppListener {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Current", Build.VERSION.SDK_INT + " current sdk int");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mHandler = new Handler();
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
            if (!database.hasValues()) {
                mHandler.post(createLooperMessage());

                finish();
            } else {
                ShowCodeActivity.startAndFinish(this);
            }
        } else {
            appManager.init(this, this);
        }
    }

    @Override
    public void onAppInitialized() {
        checkDependency();
    }

    private Runnable createLooperMessage() {
        return new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoadingActivity.this,
                        R.string.please_configure_on_phone,
                        Toast.LENGTH_SHORT).show();
            }
        };
    }
}
