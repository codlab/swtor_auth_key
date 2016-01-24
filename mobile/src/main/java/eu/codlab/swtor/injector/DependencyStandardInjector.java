package eu.codlab.swtor.injector;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import eu.codlab.swtor.database.DatabaseProvider;
import eu.codlab.swtor.network.IWeb;
import eu.codlab.swtor.network.NetworkConstants;
import eu.codlab.swtor.network.ToStringConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class DependencyStandardInjector extends DependencyInjector {
    private final static String SHARED_PREFERENCES = "swtor";

    private Context _context;
    private DatabaseProvider _database_provider;

    public DependencyStandardInjector() {
        super();
    }

    @Override
    public void init(@NonNull Context context) {
        _context = context;
        _database_provider = new DatabaseProvider();
    }

    @Override
    @NonNull
    public IWeb getNetworkTimeWebsevice() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.SWTOR)
                .addConverterFactory(new ToStringConverterFactory())
                .build();

        return retrofit.create(IWeb.class);
    }

    @Override
    @NonNull
    public SharedPreferences getDefaultSharedPreferences() {
        return _context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    @NonNull
    public DatabaseProvider getDatabaseProvider() {
        return _database_provider;
    }
}
