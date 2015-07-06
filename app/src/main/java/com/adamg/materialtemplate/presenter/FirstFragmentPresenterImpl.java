package com.adamg.materialtemplate.presenter;

import android.app.Activity;
import android.os.Bundle;

import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.ui.views.FirstFragmentView;

/**
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class FirstFragmentPresenterImpl extends FragmentBasePresenterImpl<FirstFragmentView>
        implements FirstFragmentPresenter {

    /**
     * Default abstract constructor that allows us to inject all the common pieces necessary for building
     * the implementations of this class
     *
     * @param act        Injected instance of the Activity Context
     * @param mNavigator Injected instance of the navigator for direction of the application flow
     */
    public FirstFragmentPresenterImpl(Activity act, Navigator mNavigator) {
        super(act, mNavigator);
    }

    @Override
    public void savedInstanceState(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle icicle) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void setArguments(Bundle bundle) {

    }

    @Override
    public void onSecondFragmentClick() {
        mNavigator.navigateToSecondFragment();
    }
}
