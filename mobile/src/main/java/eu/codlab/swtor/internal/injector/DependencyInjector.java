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
