package com.adamg.materialtemplate.cloud.module;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Module that provides the OkHttp module for injection. It will create it with a default cache size and
 * default the rest of the set up to the Retrofit API client for a better control.
 *
 * @author Adam Greenberg
 * @version 1 on 6/27/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
@Module(
        includes = RestApiModule.class,
        complete = false,
        library = true
)
public class OkHttpModule {

    private static final String TAG = OkHttpModule.class.getSimpleName();

    /**
     * Set the disk cache to an arbitrary size of 10 MB
     */
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024;

    /**
     * Module that provides the set up for the OkHttp client.
     *
     * @param ctx a reference to the Application's {@link Context}
     * @return the instance of the OkHttp Client used as the transport layer for API calls
     */
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@CloudLibModule.ClientApplication Context ctx) {
        return createOkHttpClient(ctx);
    }

    /**
     * Creates a default OkHttp client with a disk cache of 10 MB
     *
     * @param ctx a reference to the Client's {@link Context}
     * @return the instance of the OkHttp Client used as the transport layer for API calls
     */
    static OkHttpClient createOkHttpClient(final Context ctx) {
        OkHttpClient client = new OkHttpClient();

        // Install an HTTP cache in the application cache directory.
        try {
            final File cacheDir = new File(ctx.getCacheDir(), "https");
            final Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (final IOException e) {
            // Log the error
            Log.e(TAG, "Unable to install disk cache." + Log.getStackTraceString(e));
        }

        return client;
    }
}
