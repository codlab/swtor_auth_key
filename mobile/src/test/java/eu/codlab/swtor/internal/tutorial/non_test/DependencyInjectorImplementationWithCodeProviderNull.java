package eu.codlab.swtor.internal.tutorial.non_test;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.listeners.IAppManager;
import eu.codlab.common.dependency.listeners.IDatabaseProvider;
import eu.codlab.common.dependency.listeners.IWeb;
import eu.codlab.common.security.CodeProvider;
import eu.codlab.common.security.TimeProvider;

/**
 * Created by Kévin on 05/02/2016.
 */
public class DependencyInjectorImplementationWithCodeProviderNull implements DependencyInjector {
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
        return EventBus.builder().build();
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
