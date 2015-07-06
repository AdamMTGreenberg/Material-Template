package com.adamg.materialtemplate.navigator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.adamg.materialtemplate.ui.activities.BaseActivity;

/**
 * Class created to handle all the navigation between activities and fragments
 *
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class Navigator {

    private final Activity mActivityContext;

    private Bundle mExtras;

    public Navigator(final Activity activityContext) {
        this.mActivityContext = activityContext;
    }

    /**
     * Sets the extras so that on the navigation we can use the {@link android.app.Fragment#setArguments(Bundle)}
     * call to add additional information to the Fragment
     *
     * @param bundle data containing information
     */
    public Navigator setExtras(final Bundle bundle) {
        mExtras = bundle;
        return this;
    }

    /**
     * Navigate to a fragment with a shared element transaction
     *
     * @param fragment       fragment reference, should be of the concrete type
     * @param addToBackStack true to add to the back stack, false otherwise
     * @param view           View containing the shared elements for the transition
     */
    private void navigateWithTransaction(final Fragment fragment, final View view, final boolean addToBackStack) {
        // Set the arguments
        setArguments(fragment);

        // Navigate without adding to the back stack as we do not expect that behaviour
        final FragmentTransaction transaction = mActivityContext.getFragmentManager().beginTransaction();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            transaction.addSharedElement(view, view.getTransitionName());
        }

        navigate(fragment, transaction, addToBackStack);
    }

    /**
     * Navigate to a fragment
     *
     * @param fragment       fragment reference, should be of the concrete type
     * @param addToBackStack true to add to the back stack, false otherwise
     */
    private void navigateToFragment(final Fragment fragment, final boolean addToBackStack) {
        // Set the arguments
        setArguments(fragment);

        // Navigate without adding to the back stack as we do not expect that behaviour
        final FragmentTransaction transaction = mActivityContext.getFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
        navigate(fragment, transaction, addToBackStack);
    }

    private void navigate(final Fragment fragment, final FragmentTransaction transaction, final boolean addToBackStack) {
        // Get the res id
        final int resId = ((BaseActivity) mActivityContext).getFrameLayoutId();

        if (addToBackStack) {
            // Add this transaction to the back stack
            transaction.replace(resId, fragment, fragment.getClass().getSimpleName());
            transaction.addToBackStack(fragment.getClass().getSimpleName());

        }
        else {
            transaction.add(resId, fragment, fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }

    /**
     * One time adds the extras to the creation of the fragment
     */
    private void setArguments(final Fragment fragment) {
        if (mExtras != null) {
            fragment.setArguments(mExtras);
        }
        mExtras = null;
    }
}
