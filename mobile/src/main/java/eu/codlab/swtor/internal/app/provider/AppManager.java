package eu.codlab.swtor.internal.app.provider;

import android.content.Context;
import android.support.annotation.NonNull;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.util.ArrayList;
import java.util.List;

import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.dependency.listeners.IAppListener;
import eu.codlab.common.dependency.listeners.IAppManager;
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
                     @NonNull IAppListener listener) {
        appendListener(listener);

        if (!isInit() && !mLoading) {
            mLoading = true;
            Thread thread = new Thread() {

                @Override
                public void run() {
                    onEvent(new EventLoad(context));
                }
            };
            thread.start();
        }
    }

    @Override
    public boolean isInit() {
        return mInit;
    }

    @Override
    public void removeListener(@NonNull IAppListener listener) {
        lock();

        if (mAppListeners.contains(listener)) {
            mAppListeners.remove(listener);
        }

        unlock();
    }

    @Override
    public boolean hasListeners() {
        return !mAppListeners.isEmpty();
    }

    public void appendListener(@NonNull IAppListener listener) {
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

        for (IAppListener listener : mAppListeners) {
            listener.onAppInitialized();
        }

        lock();
        mAppListeners.removeAll(listeners);
        unlock();

        listeners.clear();
    }

    public void onEvent(EventLoad event) {
        Context context = event.getContext();

        try {
            Fabric.with(context, new Crashlytics(), new Answers());
        } catch (Throwable e) {
        }

        try {
            FlowManager.init(context);
        } catch (Exception e) {
        }

        try {
            DependencyInjectorFactory
                    .getDependencyInjector()
                    .getDatabaseProvider()
                    .loadDatabaseIntoMemory();
        } catch (Exception e) {
        }

        mInit = true;
        warnListeners();
    }

    public static class EventLoad {
        private Context mContext;

        public EventLoad(Context context) {
            mContext = context;
        }

        public Context getContext() {
            return mContext;
        }
    }
}
