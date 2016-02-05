package eu.codlab.swtor.internal.injector;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class DependencyInjectorFactory {
    private static final String INJECTOR_NOT_CREATED = "Please ensure calling "
            + DependencyInjectorFactory.class.getSimpleName()
            + ".init(Context, Injector Class) first";
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
        if (_injector == null) {
            throw new IllegalStateException(INJECTOR_NOT_CREATED);
        }
        return _injector;
    }


}
