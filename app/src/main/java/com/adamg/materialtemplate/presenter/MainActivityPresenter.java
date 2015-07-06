package com.adamg.materialtemplate.presenter;


import android.os.Bundle;

import com.adamg.materialtemplate.ui.views.ActivityView;
import com.adamg.materialtemplate.ui.views.MainActivityView;

/**
 * Presenter class that backs the business logic for the Activity
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public interface MainActivityPresenter extends Presenter<MainActivityView> {

    /**
     * Method called once the View has been created. Typically, this will set the fragment displayed in the
     *
     * @param savedInstanceState the bundle passed to the View initialization
     * @param view               the {@link com.adamg.materialtemplate.ui.views.View}
     *                           that this presenter is controlling
     */
    void created(final Bundle savedInstanceState, final ActivityView view);
}
