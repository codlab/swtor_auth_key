package eu.codlab.swtor.internal.database.provider;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import eu.codlab.swtor.annotations.ThreadSafe;
import eu.codlab.swtor.internal.database.events.DatabaseEvent;
import eu.codlab.swtor.internal.database.impl.Key;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.injector.interfaces.IDatabaseProvider;

/**
 * Created by kevinleperf on 16/01/16.
 */
public class DatabaseProvider implements IDatabaseProvider {

    @Nullable
    @ThreadSafe
    private List<Key> mDatabaseValue;

    public DatabaseProvider() {
        mDatabaseValue = null;
    }

    public boolean loadDatabaseIntoMemory() {
        if (!hasLoadedDatabaseValues()) {
            return reloadDatabaseInMemory();
        }
        return true;
    }

    public boolean reloadDatabaseInMemory() {
        mDatabaseValue = new Select()
                .from(Key.class)
                .orderBy(OrderBy.fromNameAlias(new NameAlias("updated_at")).descending())
                .queryList();
        return true;
    }

    public boolean hasLoadedDatabaseValues() {
        return mDatabaseValue != null;
    }

    public boolean hasValues() {
        return hasLoadedDatabaseValues() && !mDatabaseValue.isEmpty();
    }

    @NonNull
    public List<Key> getAllKeys() {
        List<Key> tmp = mDatabaseValue;
        if (null == tmp)
            tmp = new ArrayList<>();

        return tmp;
    }

    @NonNull
    public List<Key> getCopyKeys() {
        ArrayList<Key> list = new ArrayList<>();

        List<Key> tmp = getAllKeys();
        list.addAll(tmp);

        return list;
    }

    @Override
    public Key getLastKey() {
        if (!getAllKeys().isEmpty())
            return getAllKeys().get(0);
        return null;
    }

    @Override
    public void updateKey(Key key) {
        List<Key> keys = getAllKeys();

        if (!keys.contains(key)) {
            keys.add(key);
        }

        key.setUpdatedAt(System.currentTimeMillis());
        key.save();

        sortKeys();
    }

    @Override
    public void warnListeners() {
        Key key = getLastKey();
        DependencyInjectorFactory.getDependencyInjector()
                .getDefaultEventBus().postSticky(new DatabaseEvent(key));
    }

    private void sortKeys() {
        Collections.sort(getAllKeys(), getComparator());
    }

    private Comparator<Key> getComparator() {
        return new Comparator<Key>() {
            @Override
            public int compare(Key lhs, Key rhs) {
                if (lhs.getUpdatedAt() > rhs.getUpdatedAt())
                    return -1;
                if (lhs.getUpdatedAt() < rhs.getUpdatedAt())
                    return 1;
                return 0;
            }
        };
    }
}
