package eu.codlab.swtor.injector;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import eu.codlab.swtor.database.DatabaseProvider;
import eu.codlab.swtor.network.IWeb;

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
    public abstract DatabaseProvider getDatabaseProvider();

}
