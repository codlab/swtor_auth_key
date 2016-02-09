package eu.codlab.swtor.internal.security;

import android.support.annotation.NonNull;

import eu.codlab.swtor.internal.injector.DependencyInjector;
import eu.codlab.swtor.internal.injector.DependencyInjectorFactory;
import eu.codlab.swtor.internal.tutorial.CodeInvalidateEvent;

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