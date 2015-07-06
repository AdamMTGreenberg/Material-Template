package com.adamg.materialtemplate.presenter;


import com.adamg.materialtemplate.ui.views.MainActivityView;

/**
 * Presenter class that backs the business logic for the Activity
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public interface MainActivityPresenter extends Presenter<MainActivityView> {

    /**
     * Called when the first load of the Activity has occurred and all logic and state is at zero.
     */
    void onFirstCreation();
}
