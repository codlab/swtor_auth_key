package eu.codlab.swtor.internal.injector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import eu.codlab.swtor.BuildConfig;

/**
 * Created by kevinleperf on 15/01/16.
 */
public class DependencyInjectorFactory {
    private static final String INJECTOR_NOT_CREATED = "Please ensure calling "
            + DependencyInjectorFactory.class.getSimpleName()
            + ".init(Context, Injector Class) first";
    private static DependencyInjector sInjector = null;

    /**
     * Empty constructor to prevent class instantiation
     */
    private DependencyInjectorFactory() {

    }

    public static void init(@NonNull Context context,
                            @NonNull Class<? extends DependencyInjector> injector) {
        try {
            sInjector = injector.newInstance();
        } catch (Exception exception) {
            sInjector = new DependencyStandardInjector();
            if (BuildConfig.DEBUG) {
                Log.e(DependencyInjectorFactory.class.getSimpleName(),
                        "exception", exception);
            }
        }

        initDependencyInjectorFactory(context);
    }

    private static void initDependencyInjectorFactory(@NonNull Context context) {
        sInjector.init(context);
    }

    @NonNull
    public static DependencyInjector getDependencyInjector() {
        if (sInjector == null) {
            throw new IllegalStateException(INJECTOR_NOT_CREATED);
        }
        return sInjector;
    }


}
