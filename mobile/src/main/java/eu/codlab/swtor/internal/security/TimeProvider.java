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

    private Handler _handler;
    private Runnable _post_event;

    public TimeProvider() {
        _post_event = new Runnable() {
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
        if (_handler != null) _handler.postDelayed(_post_event, getNextIterationIn());
    }

    @MainThread
    public void onResume() {
        if (_handler == null) {
            _handler = new Handler(Looper.getMainLooper());

            _handler.post(_post_event);
        }
    }

    @MainThread
    public void onPause() {
        if (_handler != null) {
            _handler.removeCallbacks(_post_event);

            _handler = null;
        }
    }
}
