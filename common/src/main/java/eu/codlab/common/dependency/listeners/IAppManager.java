package eu.codlab.common.dependency.listeners;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by kevinleperf on 25/01/16.
 */
public interface IAppManager {
    void init(@NonNull final Context context,
              @NonNull final IAppListener listener);

    boolean isInit();

    void appendListener(IAppListener listener);

    void removeListener(IAppListener listener);

    boolean hasListeners();
}
