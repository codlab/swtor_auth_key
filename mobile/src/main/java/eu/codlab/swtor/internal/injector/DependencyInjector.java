package eu.codlab.swtor.internal.injector;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;
import eu.codlab.swtor.internal.injector.interfaces.IDatabaseProvider;
import eu.codlab.swtor.internal.network.IWeb;
import eu.codlab.swtor.internal.security.CodeProvider;
import eu.codlab.swtor.internal.security.TimeProvider;

/**
 * Created by kevinleperf on 15/01/16.
 */
public abstract class DependencyInjector {

    public abstract void init(@NonNull Context context);

    @NonNull
    public abstract IWeb getNetworkTimeWebsevice();

    @NonNull
    public abstract SharedPreferences getDefaultSharedPreferences();

    @NonNull
    public abstract IDatabaseProvider getDatabaseProvider();

    @NonNull
    public abstract EventBus getDefaultEventBus();

    @NonNull
    public abstract IAppManager getAppManager();

    @Nullable
    public abstract CodeProvider getCodeProvider(String provider);

    @NonNull
    public abstract TimeProvider getTimeProvider();

}
