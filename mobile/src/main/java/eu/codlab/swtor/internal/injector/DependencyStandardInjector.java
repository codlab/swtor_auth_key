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
public class DependencyStandardInjector implements DependencyInjector {
    private static final String SHARED_PREFERENCES = "swtor";

    private Context mContext;
    private DatabaseProvider mDatabaseProvider;
    private AppManager mAppManager;
    private TimeProvider mTimeProvider;
    private Retrofit mRetrofit;
    private IWeb mIWeb;

    public DependencyStandardInjector() {
        super();
    }

    @Override
    public void init(@NonNull Context context) {
        mContext = context;
        mDatabaseProvider = new DatabaseProvider();
        mAppManager = new AppManager();
        mTimeProvider = new TimeProvider();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(NetworkConstants.SWTOR)
                .addConverterFactory(new ToStringConverterFactory())
                .build();

        mIWeb = mRetrofit.create(IWeb.class);

    }

    Retrofit getRetrofit(){
        return mRetrofit;
    }

    @Override
    @NonNull
    public IWeb getNetworkTimeWebsevice() {
        return mIWeb;
    }

    @Override
    @NonNull
    public SharedPreferences getDefaultSharedPreferences() {
        return mContext.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    @NonNull
    public IDatabaseProvider getDatabaseProvider() {
        return mDatabaseProvider;
    }

    @NonNull
    @Override
    public EventBus getDefaultEventBus() {
        return EventBus.getDefault();
    }

    @NonNull
    @Override
    public IAppManager getAppManager() {
        return mAppManager;
    }

    @Nullable
    @Override
    public CodeProvider getCodeProvider(String code) {
        return CodeProviderFactory.getCodeProvider(code);
    }

    @NonNull
    @Override
    public TimeProvider getTimeProvider() {
        return mTimeProvider;
    }
}
