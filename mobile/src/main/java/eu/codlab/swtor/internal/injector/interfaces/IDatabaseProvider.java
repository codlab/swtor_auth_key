package eu.codlab.swtor.internal.injector.interfaces;

import android.support.annotation.NonNull;

import java.util.List;

import eu.codlab.swtor.internal.database.impl.Key;

/**
 * Created by kevinleperf on 25/01/16.
 */
public interface IDatabaseProvider {

    void loadDatabaseIntoMemory();

    void reloadDatabaseInMemory();

    boolean hasLoadedDatabaseValues();

    boolean hasValues();

    @NonNull
    List<Key> getAllKeys();

    @NonNull
    List<Key> getCopyKeys();
}
