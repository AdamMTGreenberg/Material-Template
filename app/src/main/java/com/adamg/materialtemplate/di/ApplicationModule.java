package com.adamg.materialtemplate.di;

import android.content.Context;

import com.adamg.materialtemplate.BaseApplication;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Dagger module that provides all the Application specific pieces
 *
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
@Module(library = true)
public class ApplicationModule {

    private final BaseApplication mApplication;

    public ApplicationModule(final BaseApplication application) {
        mApplication = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return mApplication;
    }

    /**
     * Interface that defines the {@link Context} as an Application context
     */
    @Qualifier
    @Retention(RUNTIME)
    public @interface ForApplication {
    }
}
