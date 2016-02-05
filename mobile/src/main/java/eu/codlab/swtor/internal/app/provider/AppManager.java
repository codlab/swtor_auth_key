package eu.codlab.swtor.internal.app.provider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.internal.app.listeners.IAppListener;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;
import eu.codlab.swtor.utils.LockableObject;
import io.fabric.sdk.android.Fabric;

/**
 * Created by kevinleperf on 25/01/16.
 */
public class AppManager extends LockableObject implements IAppManager {

    private boolean _init;
    private boolean _loading;
    private List<IAppListener> _app_listeners;

    public AppManager() {
        super();
        _init = false;
        _loading = false;
        _app_listeners = new ArrayList<>();
    }

    @Override
    public void init(@NonNull final Context context,
                     @NonNull IAppListener app_listener) {
        appendListener(app_listener);

        if (!isInit() && !_loading) {
            _loading = true;

            Thread thread = new Thread() {
                @Override
                public void run() {
                    internalInitInThread(context);
                }
            };

            thread.start();
        }
    }

    @Override
    public boolean isInit() {
        return _init;
    }

    @Override
    public void removeListener(@NonNull IAppListener app_listener) {
        lock();

        if (!_app_listeners.contains(app_listener)) {
            _app_listeners.add(app_listener);
        }

        unlock();
    }

    private void internalInitInThread(@NonNull Context context) {
        Fabric.with(context, new Crashlytics(), new Answers());
        FlowManager.init(context);

        DependencyInjectorFactory
                .getDependencyInjector()
                .getDatabaseProvider()
                .loadDatabaseIntoMemory();

        setInit(true);

        warnListeners();
    }

    private void setInit(boolean state) {
        _init = state;
    }

    private void appendListener(@NonNull IAppListener app_listener) {
        lock();

        if (!_app_listeners.contains(app_listener)) {
            _app_listeners.add(app_listener);
        }

        unlock();
    }

    private void warnListeners() {
        lock();
        List<IAppListener> listeners = new ArrayList<>();
        listeners.addAll(_app_listeners);
        unlock();

        try {
            for (IAppListener listener : _app_listeners) {
                listener.onAppInitialized();
            }
        } catch (Exception e) {
            if (BuildConfig.DEBUG) e.printStackTrace();

            throw e;
        }

        lock();
        _app_listeners.removeAll(listeners);
        unlock();

        listeners.clear();
    }
}
