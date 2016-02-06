package eu.codlab.swtor.internal.injector.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;

import eu.codlab.swtor.internal.app.listeners.IAppListener;

/**
 * Created by kevinleperf on 25/01/16.
 */
public interface IAppManager {
    void init(@NonNull final Context context,
              @NonNull final IAppListener listener);

    boolean isInit();

    void removeListener(IAppListener listener);
}
