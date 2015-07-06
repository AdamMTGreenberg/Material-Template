package com.adamg.materialtemplate.presenter;

import android.os.Bundle;

import com.adamg.materialtemplate.ui.views.View;


/**
 * Presenter to work as base for every presenter created in the application. This
 * presenter declares some methods to attach the fragment/activity lifecycle.
 *
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public interface Presenter<T extends View> {

    /**
     * Called when the presenter is initialized, this method represents the start of the presenter
     * lifecycle.
     */
    void initialize();

    /**
     * Called when the presenter is resumed. After the initialization and when the presenter comes
     * from a pause state.
     */
    void resume();

    /**
     * Called when the presenter is paused.
     */
    void pause();

    /**
     * Called when the presenter is destroyed.
     */
    void destroy();

    /**
     * Sets the base View for this Presenter to interact with
     */
    void setView(T view);

    /**
     * Sets the extras from View creation, passed into the Fragment, allowing for the sharing of
     * information between Views
     *
     * @param bundle extra information for the next View
     */
    void setArguments(Bundle bundle);

    /**
     * Method that denotes whether or not the Presenter has been destroyed
     *
     * @return true if it has, false otherwise
     */
    boolean isDestroyed();
}
