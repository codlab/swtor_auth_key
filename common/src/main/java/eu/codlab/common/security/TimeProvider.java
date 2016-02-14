package eu.codlab.common.security;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;

/**
 * Created by kevinleperf on 03/02/16.
 */
public class TimeProvider {

    private Handler mHandler;
    private Runnable mPostEvent;

    public TimeProvider() {
        mPostEvent = createPostNextIterationRunnable();
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

    protected boolean postNextIteration() {
        if (isResumed()) {
            mHandler.postDelayed(mPostEvent, getNextIterationIn());
            return true;
        }
        return false;
    }

    @MainThread
    public void onResume() {
        if (isPaused()) {
            mHandler = new Handler(Looper.getMainLooper());

            mHandler.post(mPostEvent);
        }
    }

    @MainThread
    public void onPause() {
        if (isResumed()) {
            mHandler.removeCallbacks(mPostEvent);

            mHandler = null;
        }
    }

    public boolean isResumed() {
        return mHandler != null;
    }

    public boolean isPaused() {
        return !isResumed();
    }

    private Runnable createPostNextIterationRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                InternalTimeProvider.internalRunPostedNextIteration(TimeProvider.this);
            }
        };
    }
}
