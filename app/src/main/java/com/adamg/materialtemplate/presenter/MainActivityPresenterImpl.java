package com.adamg.materialtemplate.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.ui.views.ActivityView;
import com.adamg.materialtemplate.ui.views.MainActivityView;

/**
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
class MainActivityPresenterImpl extends BasePresenterImpl<MainActivityView> implements MainActivityPresenter {

    /**
     * Default abstract constructor that allows us to inject all the common pieces necessary for building
     * the implementations of this class
     *
     * @param act Injected instance of the Activity Context
     * @param mNavigator   Injected instance of the navigator for direction of the application flow
     */
    public MainActivityPresenterImpl(Activity act, Navigator mNavigator) {
        super(act, mNavigator);
    }

    @Override
    public void created(Bundle savedInstanceState, ActivityView view) {
        // Set the Fragment container
        mNavigator.setFragmentContainer(view.getFragmentContainer());
        if(savedInstanceState == null) {
            mNavigator.navigateToFirstFragment();
        }
    }

    @Override
    public void resume() {
        // Nothing to do
    }

    @Override
    public void pause() {
        // Nothing to do
    }

    @Override
    public void setArguments(Bundle bundle) {
        // Nothing to do
    }
}
