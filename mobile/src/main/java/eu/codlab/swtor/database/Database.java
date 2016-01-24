package eu.codlab.swtor.database;

/**
 * Created by kevinleperf on 08/01/16.
 */
@com.raizlabs.android.dbflow.annotation.Database(
        name = Database.NAME,
        version = Database.VERSION
)
public class Database {
    public final static String NAME = "keys";
    public final static int VERSION = 1;
}
