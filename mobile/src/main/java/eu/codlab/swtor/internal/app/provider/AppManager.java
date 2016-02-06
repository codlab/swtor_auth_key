package eu.codlab.swtor.internal.app.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.wopata.aspectlib.annotations.EnsureAsync;

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

    private boolean mInit;
    private boolean mLoading;
    private List<IAppListener> mAppListeners;

    public AppManager() {
        super();
        mInit = false;
        mLoading = false;
        mAppListeners = new ArrayList<>();
    }

    @Override
    public void init(@NonNull final Context context,
                     @NonNull IAppListener app_listener) {
        appendListener(app_listener);

        if (!isInit() && !mLoading) {
            mLoading = true;

            internalInitInThread(context);
        }
    }

    @Override
    public boolean isInit() {
        return mInit;
    }

    @Override
    public void removeListener(@NonNull IAppListener listener) {
        lock();

        if (!mAppListeners.contains(listener)) {
            mAppListeners.add(listener);
        }

        unlock();
    }

    @EnsureAsync
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
        mInit = state;
    }

    private void appendListener(@NonNull IAppListener listener) {
        lock();

        if (!mAppListeners.contains(listener)) {
            mAppListeners.add(listener);
        }

        unlock();
    }

    private void warnListeners() {
        lock();
        List<IAppListener> listeners = new ArrayList<>();
        listeners.addAll(mAppListeners);
        unlock();

        try {
            for (IAppListener listener : mAppListeners) {
                listener.onAppInitialized();
            }
        } catch (Exception exception) {
            if (BuildConfig.DEBUG) {
                Log.e(AppManager.class.getSimpleName(), "exception ", exception);
            }

            throw exception;
        }

        lock();
        mAppListeners.removeAll(listeners);
        unlock();

        listeners.clear();
    }
}
