package com.adamg.materialtemplate.ui.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.adamg.materialtemplate.BaseApplication;
import com.adamg.materialtemplate.di.Injector;
import com.adamg.materialtemplate.di.ViewModule;
import com.adamg.materialtemplate.ui.views.View;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import rx.android.internal.Preconditions;


public abstract class BaseActivity extends AppCompatActivity implements View,
        Injector {

    /**
     * Activity scope graph use to manage the DI pieces of Dagger
     */
    private ObjectGraph mActivityScopeGraph;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        // Create a new Dagger ObjectGraph
        injectDependencies();

        // Inject all annotated Views
        ButterKnife.inject(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        mActivityScopeGraph = null;

        super.onDestroy();
    }

    /**
     * Gets this Activity's object graph.
     *
     * @return the object graph
     */
    @Override
    public final ObjectGraph getObjectGraph() {
        return mActivityScopeGraph;
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(final @NonNull Object object) {
        Preconditions.checkState(mActivityScopeGraph != null, "object graph must be assigned prior to calling inject");
        mActivityScopeGraph.inject(object);
    }

    /**
     * Obtains a reference to the FrameLayout used for Fragment swapping
     *
     * @return Layout resource ID for the FrameLayout housing the fragments
     */
    @IdRes
    public abstract int getFrameLayoutId();

    /**
     * Obtains a reference to the Layout for injection via ButterKnife
     *
     * @return Layout resource ID for the layout
     */
    @LayoutRes
    public abstract int getLayout();

    /**
     * Get a list of Dagger modules with Activity scope needed to this Activity.
     *
     * @return modules with new dependencies to provide.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ViewModule(this));
    }

    /**
     * Create a new Dagger ObjectGraph to add new dependencies using a plus operation and inject the
     * declared one in the activity. This new graph will be destroyed once the activity lifecycle
     * finish.
     * <p/>
     * This is the key of how to use Activity scope dependency injection.
     */
    private void injectDependencies() {
        // Get a reference to the Application
        BaseApplication application = (BaseApplication) getApplication();
        mActivityScopeGraph = application.getApplicationGraph().plus(getModules().toArray());
        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        inject(this);
    }


}
