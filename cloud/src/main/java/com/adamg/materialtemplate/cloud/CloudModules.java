package com.adamg.materialtemplate.cloud;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows for the modules to be basically shadowed at runtime so that in testing the class
 * we can override where necessary when building the {@link dagger.ObjectGraph}. All additions to
 * the {@link List} returned within {strong} MUST {strong} be called before calling
 * {@link CloudDelegate#go(Context)} for the first time.
 * <br>
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
final class CloudModules {

    /**
     * List of Objects to be returned as {@link dagger.Module} when building the new dependency graph
     */
    static List<Object> sModuleList = new ArrayList<>();

    /**
     * Add the {@link Object} to the {@link List} of items constructed in the
     * {@link dagger.ObjectGraph#create(Object...)} method
     *
     * @param objects items to be added to the list
     */
    static void addToList(final Object... objects) {
        // Iterate through all objects
        for (final Object object : objects) {
            // Add it to the list
            sModuleList.add(object);
        }
    }

    /**
     * Returns the list of items to be added to the {@link dagger.ObjectGraph} when the
     * {@link dagger.ObjectGraph#create(Object...)} is called
     *
     * @return a list of Modules to be injected
     */
    static Object[] getModules() {
        return sModuleList.toArray(new Object[sModuleList.size()]);
    }
}
