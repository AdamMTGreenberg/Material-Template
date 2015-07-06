package com.adamg.materialtemplate.cloud;

import android.content.Context;
import android.util.Log;

import com.adamg.materialtemplate.cloud.module.CloudLibModule;

import dagger.ObjectGraph;

/**
 * This class models the entry point for this portion of the library. It will allow for all parts of the
 * library to be register, accessed, and assembled.
 *
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class CloudDelegate implements ICloudDelegate {

    private static final String TAG = CloudDelegate.class.getSimpleName();

    /**
     * Instance of the library
     */
    private static volatile CloudDelegate sInstance;

    /**
     * The DI graph
     */
    ObjectGraph mObjectGraph;

    /**
     * Default constructor
     *
     * @param ctx Client's (Application) context
     */
    private CloudDelegate(final Context ctx) {
        // Add it to the list of modules
        CloudModules.addToList(new CloudLibModule(ctx));

        // Init the ObjectGraph
        mObjectGraph = ObjectGraph.create(CloudModules.getModules());

        // Inject self dependency
        inject(this);
    }


    /**
     * Directly inject a {@link java.lang.Object} into the Dagger DI graph
     *
     * @param object the Object which needs a specific dependency supplied by the system
     */
    public void inject(final Object object) {
        // If we can't add to the graph, throw an error
        if (mObjectGraph == null) {
            final String error = "Cannot inject the object " + object.getClass().getSimpleName() + " into the ObjectGraph "
                    + "because it is null";

            Log.e(TAG, error);

            throw new IllegalArgumentException(error);
        }

        // Inject the Object
        mObjectGraph.inject(object);
    }

    /**
     * Builder piece allowing for the library to obtain the library all together
     *
     * @param ctx reference to the Client's context
     * @return a new instance of {@link CloudDelegate} ready to execute API calls
     */
    public static CloudDelegate go(final Context ctx) {
        synchronized (TAG) {
            if (sInstance == null) {
                sInstance = new Builder(ctx).build();
            }

            return sInstance;
        }
    }

    /**
     * Inner builder class
     */
    static class Builder { // Builder for extensibility in the future

        /**
         * Reference to the Application's context
         */
        private final Context mCtx;

        Builder(final Context ctx) {
            mCtx = ctx;
        }

        /**
         * Builds a new instance of the {@link CloudDelegate}
         */
        public CloudDelegate build() {
            return new CloudDelegate(mCtx);
        }
    }
}
