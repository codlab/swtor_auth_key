package eu.codlab.swtor.internal.app.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.wopata.aspectlib.annotations.EnsureUiThread;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
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
                     @NonNull IAppListener listener) {
        appendListener(listener);

        if (!isInit() && !mLoading) {
            mLoading = true;

            EventBus.getDefault().register(this);

            EventBus.getDefault().post(new EventLoad(context));
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

    @EnsureUiThread
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

    @Subscribe(threadMode = ThreadMode.Async)
    public void onEvent(EventLoad event) {
        Context context = event.getContext();

        try {
            Fabric.with(context, new Crashlytics(), new Answers());
            FlowManager.init(context);

            DependencyInjectorFactory
                    .getDependencyInjector()
                    .getDatabaseProvider()
                    .loadDatabaseIntoMemory();

        } catch (Exception e) {
        }

        EventBus.getDefault().unregister(this);

        warnListeners();
        mInit = true;
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
