package eu.codlab.swtor.internal.database.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.List;

import eu.codlab.swtor.annotations.ThreadSafe;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.injector.interfaces.IDatabaseProvider;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class DatabaseProvider implements IDatabaseProvider {

    @Nullable
    @ThreadSafe
    private List<Key> _database_value = null;

    public DatabaseProvider() {

    }

    public void loadDatabaseIntoMemory() {
        if (!hasLoadedDatabaseValues()) {
            reloadDatabaseInMemory();
        }
    }

    public void reloadDatabaseInMemory() {
        _database_value = new Select()
                .from(Key.class)
                .queryList();
    }

    public boolean hasLoadedDatabaseValues() {
        List<Key> list = _database_value;
        return list != null;
    }

    public boolean hasValues() {
        return _database_value != null && _database_value.size() > 0;
    }

    @NonNull
    public List<Key> getAllKeys() {
        List<Key> tmp = _database_value;

        return tmp == null ? new ArrayList<Key>() : tmp;
    }

    @NonNull
    public List<Key> getCopyKeys() {
        ArrayList<Key> list = new ArrayList<>();

        List<Key> tmp = getAllKeys();
        list.addAll(tmp);

        return list;
    }
}
