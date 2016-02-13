package eu.codlab.swtor.internal.database.events;

import android.support.annotation.Nullable;

import eu.codlab.swtor.internal.database.impl.Key;

/**
 * Created by kevinleperf on 10/02/16.
 */
public class DatabaseEvent {
    @Nullable
    private Key mKey;

    public DatabaseEvent(@Nullable Key key) {
        mKey = key;
    }

    @Nullable
    public Key getKey() {
        return mKey;
    }
}
