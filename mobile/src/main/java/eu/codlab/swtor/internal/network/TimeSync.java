package eu.codlab.swtor.internal.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class TimeSync {
    private static TimeSync sInstance = new TimeSync();
    private Context mContext;

    public static TimeSync getInstance() {
        return sInstance;
    }


    public void init(Context context) {
        mContext = context;
    }

    public void deinit() {
        mContext = null;
    }

    public void sync() {
        if (mContext != null) {
            IWeb web = DependencyInjectorFactory.getDependencyInjector()
                    .getNetworkTimeWebsevice();

            Call<String> result = web.getRoot();
            result.enqueue(getCallback());
        }
    }

    private Callback<String> getCallback() {
        return new Callback<String>() {
            @Override
            public void onResponse(@NonNull Response<String> response) {
                Headers headers = response.headers();
                if (headers != null) {
                    String stringDate = headers.get("Date");
                    Date date = new Date(stringDate);
                    long diff = date.getTime() - System.currentTimeMillis();
                    Log.d(TimeSync.class.getSimpleName(), "diff := " + diff);
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
