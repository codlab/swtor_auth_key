package eu.codlab.common.network;

import retrofit2.Callback;

/**
 * Created by kevinleperf on 11/02/16.
 */
public abstract class CallbackString implements Callback<String> {

    private Throwable mThrowable;

    @Override
    public void onFailure(Throwable t) {
        mThrowable = t;
    }

    public Throwable getError() {
        return mThrowable;
    }

    public boolean hasError() {
        return mThrowable != null;
    }
}
