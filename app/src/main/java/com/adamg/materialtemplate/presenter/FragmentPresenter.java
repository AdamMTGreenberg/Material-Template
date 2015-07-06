package com.adamg.materialtemplate.presenter;

import android.os.Bundle;

import com.adamg.materialtemplate.ui.views.View;


/**
 * @author Adam Greenberg
 * @version 1 on 6/30/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public interface FragmentPresenter<T extends View> extends Presenter<T> {

    void savedInstanceState(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle icicle);
}
