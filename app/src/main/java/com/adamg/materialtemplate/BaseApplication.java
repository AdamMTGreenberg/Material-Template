package com.adamg.materialtemplate;

import android.app.Application;

import com.adamg.materialtemplate.cloud.CloudDelegate;
import com.adamg.materialtemplate.di.ApplicationModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Class that performs all the logic for the Application on init
 *
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class BaseApplication extends Application {

    private ObjectGraph mApplicationGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        // Init the Cloud library
        CloudDelegate.go(this);

        // Init the Application Object Graph
        mApplicationGraph = ObjectGraph.create(getModules().toArray());
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ApplicationModule(this));
    }

    /**
     * Obtains the instance of the Application graph
     */
    public ObjectGraph getApplicationGraph() {
        return mApplicationGraph;
    }
}
