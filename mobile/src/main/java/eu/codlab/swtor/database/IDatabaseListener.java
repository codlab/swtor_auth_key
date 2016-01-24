package eu.codlab.swtor.database;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by kevinleperf on 16/01/16.
 */
public interface IDatabaseListener {

    void onGameKeys(@NonNull List<Key> keys);

}
