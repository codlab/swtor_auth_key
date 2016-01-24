package eu.codlab.swtor.injector;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class DependencyInjectorFactory {
    private static DependencyInjector _injector = null;

    public static void init(@NonNull Context context,
                            @NonNull Class<? extends DependencyInjector> injector) {
        try {
            _injector = injector.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            _injector = new DependencyStandardInjector();
        }

        initDependencyInjectorFactory(context);
    }

    private static void initDependencyInjectorFactory(@NonNull Context context) {
        _injector.init(context);
    }

    @NonNull
    public static DependencyInjector getDependencyInjector() {
        return _injector;
    }


}
