package eu.codlab.swtor.internal.database.events;

import eu.codlab.swtor.internal.database.impl.Key;

/**
 * Created by kevinleperf on 10/02/16.
 */
public class DatabaseEvent {
    private Key mKey;

    public DatabaseEvent(Key key) {
        mKey = key;
    }

    public Key getKey() {
        return mKey;
    }
}
