package eu.codlab.swtor.internal.network;

import android.support.annotation.NonNull;

import java.util.Date;

import eu.codlab.swtor.BuildConfig;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kevinleperf on 15/01/16.
 */
public final class TimeSync {
    private static TimeSync sInstance = new TimeSync();
    private IWeb mIWeb;

    private TimeSync() {
        //PREVENT CONSTRUCTION OUTSIDE OF THE PACKAGE
    }

    public static TimeSync getInstance() {
        return sInstance;
    }

    public void init() {
        mIWeb = DependencyInjectorFactory.getDependencyInjector()
                .getNetworkTimeWebsevice();
    }

    public void deinit() {
        mIWeb = null;
    }

    public boolean sync() {
        if (isInit()) {

            Call<String> result = mIWeb.getRoot();
            result.enqueue(getCallback());

            return true;
        }

        return false;
    }

    public boolean isInit() {
        return mIWeb != null;
    }

    private Callback<String> getCallback() {
        return new Callback<String>() {
            @Override
            public void onResponse(@NonNull Response<String> response) {
                Headers headers = response.headers();
                if (headers != null) {
                    String stringDate = headers.get("Date");
                    Date date = new Date(stringDate);
                    long diff = Math.abs(date.getTime() - System.currentTimeMillis());
                    if (BuildConfig.DEBUG)
                        assert diff > 0;
                }
            }

            /**
             * Failure called when a network error occured
             *
             * @param t the error
             */
            @Override
            public void onFailure(@NonNull Throwable t) {
                //NOTHING TO DO HERE FOR NOW
            }
        };
    }
}
