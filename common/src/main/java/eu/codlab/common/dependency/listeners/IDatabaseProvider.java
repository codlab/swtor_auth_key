package eu.codlab.common.dependency.listeners;

import android.support.annotation.NonNull;

import java.util.List;

import eu.codlab.swtor.internal.database.impl.Key;

/**
 * Created by kevinleperf on 25/01/16.
 */
public interface IDatabaseProvider {

    boolean loadDatabaseIntoMemory();

    boolean reloadDatabaseInMemory();

    boolean hasLoadedDatabaseValues();

    boolean hasValues();

    @NonNull
    List<Key> getAllKeys();

    @NonNull
    List<Key> getCopyKeys();

    Key getLastKey();

    void updateKey(Key key);

    void warnListeners();
}
