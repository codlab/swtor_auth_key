package eu.codlab.swtor.internal.injector;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import eu.codlab.swtor.internal.app.provider.AppManager;
import eu.codlab.swtor.internal.database.provider.DatabaseProvider;
import eu.codlab.swtor.internal.injector.interfaces.IAppManager;
import eu.codlab.swtor.internal.injector.interfaces.IDatabaseProvider;
import eu.codlab.swtor.internal.network.IWeb;
import eu.codlab.swtor.internal.network.NetworkConstants;
import eu.codlab.swtor.internal.network.ToStringConverterFactory;
import eu.codlab.swtor.internal.security.CodeProvider;
import eu.codlab.swtor.internal.security.CodeProviderFactory;
import eu.codlab.swtor.internal.security.TimeProvider;
import retrofit2.Retrofit;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class DependencyStandardInjector extends DependencyInjector {
    private final static String SHARED_PREFERENCES = "swtor";

    private Context _context;
    private DatabaseProvider _database_provider;
    private AppManager _app_manager;
    private TimeProvider _time_provider;

    public DependencyStandardInjector() {
        super();
    }

    @Override
    public void init(@NonNull Context context) {
        _context = context;
        _database_provider = new DatabaseProvider();
        _app_manager = new AppManager();
        _time_provider = new TimeProvider();
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
    public IDatabaseProvider getDatabaseProvider() {
        return _database_provider;
    }

    @NonNull
    @Override
    public EventBus getDefaultEventBus() {
        return EventBus.getDefault();
    }

    @NonNull
    @Override
    public IAppManager getAppManager() {
        return _app_manager;
    }

    @Nullable
    @Override
    public CodeProvider getCodeProvider(String code) {
        return CodeProviderFactory.getCodeProvider(code);
    }

    @NonNull
    @Override
    public TimeProvider getTimeProvider() {
        return _time_provider;
    }
}
