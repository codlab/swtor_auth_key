package eu.codlab.common.security;

import android.support.annotation.NonNull;

import eu.codlab.common.dependency.DependencyInjector;
import eu.codlab.common.dependency.DependencyInjectorFactory;
import eu.codlab.common.security.events.CodeInvalidateEvent;

/**
 * Created by kevinleperf on 07/02/16.
 */

final class InternalTimeProvider {

    private InternalTimeProvider() {

    }

    public static CodeInvalidateEvent postEvent(@NonNull DependencyInjector injector, @NonNull CodeInvalidateEvent event) {
        injector.getDefaultEventBus().postSticky(event);
        return event;
    }

    public static boolean internalRunPostedNextIteration(@NonNull TimeProvider provider) {
        CodeInvalidateEvent event = new CodeInvalidateEvent(provider.getNextIterationIn());

        postEvent(DependencyInjectorFactory.getDependencyInjector(), event);

        return provider.postNextIteration();
    }
}