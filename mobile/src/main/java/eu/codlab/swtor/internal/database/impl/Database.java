package eu.codlab.swtor.internal.database.impl;

/**
 * Created by kevinleperf on 08/01/16.
 */
@com.raizlabs.android.dbflow.annotation.Database(
        name = Database.NAME,
        version = Database.VERSION
)
public class Database {
    public static final String NAME = "keys";
    public static final int VERSION = 1;


    /**
     * Prevent the Database class to being instantiated
     */
    private Database() {

    }
}
