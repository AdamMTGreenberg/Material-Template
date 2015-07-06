package com.adamg.materialtemplate.ui.views;

import android.support.annotation.IdRes;

/**
 * Interface that defines the Activity that manages the main View and navigation within
 * <br>
 * Created by Adam Greenberg on 2/18/15.
 * Copyright (c) 2014 Mark One Lifestyle, Inc. All rights reserved.
 */
public interface ActivityView extends View {

    /**
     * Obtains an instance of the ID which corresponds to the ViewGroup that houses the the fragments
     *
     * @return integer that corresponds to the ViewGroup containing the fragments. Should correspond to a
     * {@link android.widget.FrameLayout}
     */
    @IdRes
    int getFragmentContainer();

}
