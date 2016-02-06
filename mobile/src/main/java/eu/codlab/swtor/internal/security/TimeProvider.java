package eu.codlab.swtor.internal.security;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.util.Log;

import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;

/**
 * Created by kevinleperf on 03/02/16.
 */
public class TimeProvider {

    private Handler mHandler;
    private Runnable mPostEvent;

    public TimeProvider() {
        mPostEvent = new Runnable() {
            @Override
            public void run() {
                Log.d("TimeProvider", "next in :: " + getNextIterationIn());
                DependencyInjectorFactory.getDependencyInjector()
                        .getDefaultEventBus().postSticky(new CodeInvalidateEvent(getNextIterationIn()));

                postNextIteration();
            }
        };
    }

    public long getNextIterationIn() {
        return getNextIteration() - System.currentTimeMillis();
    }

    public long getNextIteration() {
        return getCurrentIterationStart() + SecurityConstants.INTERVAL + 1;
    }

    public long getCurrentIterationStart() {
        return getCurrentInterval() * SecurityConstants.INTERVAL;
    }

    public long getCurrentInterval() {
        return System.currentTimeMillis() / SecurityConstants.INTERVAL;
    }

    private void postNextIteration() {
        if (mHandler != null) mHandler.postDelayed(mPostEvent, getNextIterationIn());
    }

    @MainThread
    public void onResume() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());

            mHandler.post(mPostEvent);
        }
    }

    @MainThread
    public void onPause() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mPostEvent);

            mHandler = null;
        }
    }
}
