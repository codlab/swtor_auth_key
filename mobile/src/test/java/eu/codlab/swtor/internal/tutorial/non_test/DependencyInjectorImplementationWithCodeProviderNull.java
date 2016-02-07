package eu.codlab.swtor.internal.tutorial.non_test;

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
 * Created by Kévin on 05/02/2016.
 */
public class DependencyInjectorImplementationWithCodeProviderNull implements eu.codlab.swtor.internal.injector.DependencyInjector {
    @Override
    public void init(@NonNull Context context) {

    }

    @NonNull
    @Override
    public IWeb getNetworkTimeWebsevice() {
        return null;
    }

    @NonNull
    @Override
    public SharedPreferences getDefaultSharedPreferences() {
        return null;
    }

    @NonNull
    @Override
    public IDatabaseProvider getDatabaseProvider() {
        return null;
    }

    @NonNull
    @Override
    public EventBus getDefaultEventBus() {
        return null;
    }

    @NonNull
    @Override
    public IAppManager getAppManager() {
        return null;
    }

    @Nullable
    @Override
    public CodeProvider getCodeProvider(String provider) {
        return null;
    }

    @NonNull
    @Override
    public TimeProvider getTimeProvider() {
        return new TimeProvider();
    }
}
