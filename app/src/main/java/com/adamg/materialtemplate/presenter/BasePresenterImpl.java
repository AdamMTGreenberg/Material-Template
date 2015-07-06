package com.adamg.materialtemplate.presenter;

import android.app.Activity;

import com.adamg.materialtemplate.external.SubscriptionManager;
import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.ui.views.View;
import com.adamg.materialtemplate.util.ProviderSubscriptionManager;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Base Presenter implementation. This allows us to encapsulate all common logic in one place. Idea it
 * that there is a common set of logic that needs to be duplicated for each presenter.
 *
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
abstract class BasePresenterImpl<T extends View> implements Presenter<T> {

    /**
     * Instance of the View tied to this presenter
     */
    protected T mView;

    /**
     * Injected instance of the Application Context
     */
    protected Activity mActivity;

    /**
     * Injected instance of the navigator for direction of the application flow
     */
    protected Navigator mNavigator;

    /**
     * Base instance of the subscription manager for all Fragments
     */
    private final SubscriptionManager<? extends Presenter> mSubscriptionManager;

    /**
     * Flag on the status of the initialization/destruction of the presenter
     */
    private boolean mIsDestroyed = true;

    /**
     * Default abstract constructor that allows us to inject all the common pieces necessary for building
     * the implementations of this class
     *
     * @param act        Injected instance of the Activity Context
     * @param mNavigator Injected instance of the navigator for direction of the application flow
     */
    public BasePresenterImpl(final Activity act, final Navigator mNavigator) {
        this.mActivity = act;
        this.mNavigator = mNavigator;
        mSubscriptionManager = new ProviderSubscriptionManager(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final T view) {
        this.mView = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        // Notify of initialization
        setIsDestroyed(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroy() {

        // Clear any subscriptions
        if (mSubscriptionManager != null) {
            mSubscriptionManager.unsubscribeAll();
        }

        // Notify of tear down
        setIsDestroyed(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDestroyed() {
        return mIsDestroyed;
    }

    /**
     * Method that allows for automatic binding to the subscription manager in the presenter
     *
     * @param observable patter that implements the RxJava {@link Observable} for reactive usage
     * @param observer   Callback for the emission of the data
     * @param <O>        type of data being subscribed to and emitted
     * @return Subscription instance that should be stored only in the case that the Presenter needs
     * to store and unsubscribe individual streams after/before resolution
     */
    protected final <O> Subscription subscribe(final Observable<O> observable, final Observer<O> observer) {
        return mSubscriptionManager.subscribe(observable, observer);
    }

    /**
     * Method that allows for automatic binding to the subscription manager in the presenter
     *
     * @param observable patter that implements the RxJava {@link Observable} for reactive usage
     * @param observer   Callback for the emission of the data
     * @param <O>        type of data being subscribed to and emitted
     * @return Subscription instance that should be stored only in the case that the Presenter needs
     * to store and unsubscribe individual streams after/before resolution
     */
    protected final <O> Subscription subscribeAsync(final Observable<O> observable, final Observer<O> observer) {
        return mSubscriptionManager.subscribeAsync(observable, observer);
    }

    /**
     * Method that allows subclasses to unsubscribe a single {@link Subscription}
     *
     * @param subscription {@link Subscription} tied to the presenter
     */
    protected final void unsubscribe(final Subscription subscription) {
        mSubscriptionManager.unsubscribe(subscription);
    }

    /**
     * Sets the flag on the status of the presenter
     */
    private void setIsDestroyed(final boolean isDestroyed) {
        mIsDestroyed = isDestroyed;
    }
}
