package com.adamg.materialtemplate.presenter;

import android.app.Activity;

import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.ui.views.View;

/**
 * @author Adam Greenberg
 * @version 1 on 6/30/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
abstract class FragmentBasePresenterImpl<T extends View> extends BasePresenterImpl<T>  implements FragmentPresenter<T> {
    /**
     * Default abstract constructor that allows us to inject all the common pieces necessary for building
     * the implementations of this class
     *
     * @param act        Injected instance of the Activity Context
     * @param mNavigator Injected instance of the navigator for direction of the application flow
     */
    public FragmentBasePresenterImpl(Activity act, Navigator mNavigator) {
        super(act, mNavigator);
    }

}
