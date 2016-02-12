package eu.codlab.swtor.internal.network;

import android.support.annotation.NonNull;

import java.util.Date;

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
    private long mDiff;

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

    public long getDiff() {
        return mDiff;
    }

    private Callback<String> getCallback() {
        return new CallbackString() {
            @Override
            public void onResponse(@NonNull Response<String> response) {
                //response headers are always non null
                Headers headers = response.headers();
                String stringDate = headers.get("Date");
                Date date = new Date(stringDate);
                long diff = date.getTime() - System.currentTimeMillis();

                setDiff(diff);
            }
        };
    }

    private void setDiff(long diff) {
        mDiff = diff;
    }
}
