package eu.codlab.swtor.internal.network;

import android.content.Context;
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
public class TimeSync {
    private static TimeSync sInstance = new TimeSync();

    public static TimeSync getInstance() {
        return sInstance;
    }

    private Context mContext;

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
            result.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Response<String> response) {
                    if (response != null) {
                        Headers headers = response.headers();
                        if (headers != null) {
                            String string_date = headers.get("Date");
                            Date date = new Date(string_date);
                            long diff = date.getTime() - System.currentTimeMillis();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Throwable t) {

                }
            });
        }
    }
}
