package com.adamg.materialtemplate.cloud.module;

/**
 * The module that provides the Application Context to all the other modules. Will allow us to inject
 * this as soon as the {@link dagger.ObjectGraph} is built in the {@link com.adamg.materialtemplate.cloud.CloudDelegate}.
 *
 * @author Adam Greenberg
 * @version 1 on 6/27/15
 * All code under The MIT License (MIT) unless otherwise noted.
 */

import android.content.Context;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module(
        includes = {
                OkHttpModule.class
        }
)
public class CloudLibModule {

    private final Context mCtx;

    public CloudLibModule(final Context ctx) {
        mCtx = ctx;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ClientApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ClientApplication
    Context provideApplicationContext() {
        return mCtx;
    }

    /**
     * Interface that defines the {@link Context} as an Application context
     */
    @Qualifier
    @Retention(RUNTIME)
    public @interface ClientApplication {
    }
}
