package com.adamg.materialtemplate.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adamg.materialtemplate.di.InjectingFragmentModule;
import com.adamg.materialtemplate.di.Injector;
import com.adamg.materialtemplate.presenter.FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import rx.android.internal.Preconditions;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class BaseFragment extends Fragment implements com.adamg.materialtemplate.ui.views.View, Injector {

    private ObjectGraph mObjectGraph;
    private boolean mFirstAttach = true;

    /**
     * Creates an object graph for this Fragment by extending the hosting Activity's object graph with the modules
     * returned by {@link #getModules()}.
     * <p/>
     * Injects this Fragment using the created graph.
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // expand the activity graph with the fragment-specific module(s)
        ObjectGraph appGraph = ((Injector) activity).getObjectGraph();
        List<Object> fragmentModules = getModules();
        mObjectGraph = appGraph.plus(fragmentModules.toArray());

        // Make sure it's the first time through; we don't want to re-inject a retained fragment that is going
        // through a detach/attach sequence.
        if (mFirstAttach) {
            inject(this);
            mFirstAttach = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        // Inject the Views via ButterKnife
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set the View
        getPresenter().setView(this);
        // Set any arguments
        final Bundle bundle = getArguments();
        if (bundle != null) {
            getPresenter().setArguments(bundle);
        }
        if(savedInstanceState != null) {
            getPresenter().savedInstanceState(savedInstanceState);
        }
        // Call the init method now that they View is all set up
        getPresenter().initialize();
    }

    @Override
    public void onSaveInstanceState(final Bundle icicle) {
        if(getPresenter() != null) {
            getPresenter().onSaveInstanceState(icicle);
        }
        super.onSaveInstanceState(icicle);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().resume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().destroy();
    }

    @Override
    public void onDestroy() {
        // Eagerly clear the reference to the object graph to allow it to be garbage collected as
        // soon as possible.
        mObjectGraph = null;

        super.onDestroy();
    }

    /**
     * Injects a target object using this Fragment's object graph.
     *
     * @param target the target object
     */
    @Override
    public void inject(Object target) {
        Preconditions.checkNotNull(mObjectGraph, "object graph must be assigned prior to calling inject");
        mObjectGraph.inject(target);
    }

    /**
     * Gets this Fragment's object graph.
     *
     * @return the object graph
     */
    @Override
    public final ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }

    /**
     * Returns the list of dagger modules to be included in this Fragment's object graph.  Subclasses that override
     * this method should add to the list returned by super.getModules().
     *
     * @return the list of modules
     */
    protected List<Object> getModules() {
        List<Object> result = new ArrayList<>();
        result.add(new InjectingFragmentModule(this, this));
        return result;
    }

    /**
     * Method used to obtain the layout ID for the fragment
     */
    @LayoutRes
    protected abstract int getFragmentLayout();

    /**
     * Obtains the Fragment specific presenter piece
     *
     * @return {@link com.adamg.materialtemplate.presenter.Presenter} that is specific to the class
     */
    @NonNull
    protected abstract FragmentPresenter getPresenter();
}
