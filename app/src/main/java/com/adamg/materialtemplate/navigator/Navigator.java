package com.adamg.materialtemplate.navigator;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

import com.adamg.materialtemplate.ui.activities.BaseActivity;
import com.adamg.materialtemplate.ui.fragment.FirstFragment;
import com.adamg.materialtemplate.ui.views.ActivityView;
import com.adamg.materialtemplate.ui.views.SecondFragment;

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

    /**
     * Base fragment container
     */
    private int mContainer = 0;

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
     * Navigates to the first fragment
     */
    public void navigateToFirstFragment() {
        Fragment f = FirstFragment.newInstance();
        navigateToFragment(f, false);
    }

    /**
     * Navigates to the second fragment
     */
    public void navigateToSecondFragment() {
        Fragment f = SecondFragment.newInstance();
        navigateToFragment(f, true);
    }

    /**
     * Set the main container for the fragment to occupy and transition between
     *
     * @param container the ViewGroup's ID that allows the fragment to live within
     */
    public void setFragmentContainer(@IdRes final int container) {
        mContainer = container;
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
        // Check and see if we can automatically set the value in the instance it hasn't been properly set
        if (mContainer == 0 && mActivityContext instanceof ActivityView) {
            // Set the container ID automatically for this instance
            setFragmentContainer(((ActivityView) mActivityContext).getFragmentContainer());
        }

        // Ensure we have a container to house the fragments
        if (mContainer == 0) {
            throw new IllegalStateException("Cannot navigate to a fragment while the ViewGroup container is" +
                    "null");
        }

        if (addToBackStack) {
            // Add this transaction to the back stack
            transaction.replace(mContainer, fragment, fragment.getClass().getSimpleName());
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        else {
            transaction.add(mContainer, fragment, fragment.getClass().getSimpleName());
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
