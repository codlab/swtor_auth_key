package eu.codlab.swtor.internal.network;

import android.support.annotation.NonNull;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kevinleperf on 08/01/16.
 */
public interface IWeb {

    @NonNull
    @GET("/")
    Call<String> getRoot();
}
