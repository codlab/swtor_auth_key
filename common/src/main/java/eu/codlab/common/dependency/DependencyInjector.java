package eu.codlab.common.dependency;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.common.dependency.listeners.IWeb;
import eu.codlab.common.security.CodeProvider;
import eu.codlab.common.security.TimeProvider;

/**
 * Created by kevinleperf on 15/01/16.
 */
public interface DependencyInjector {

    void init(@NonNull Context context);

    @NonNull
    IWeb getNetworkTimeWebsevice();

    @NonNull
    SharedPreferences getDefaultSharedPreferences();

    @NonNull
    IDatabaseProvider getDatabaseProvider();

    @NonNull
    EventBus getDefaultEventBus();

    @NonNull
    IAppManager getAppManager();

    @Nullable
    CodeProvider getCodeProvider(String provider);

    @NonNull
    TimeProvider getTimeProvider();
}
