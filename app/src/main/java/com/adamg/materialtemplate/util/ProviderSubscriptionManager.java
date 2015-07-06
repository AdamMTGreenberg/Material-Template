package com.adamg.materialtemplate.util;

import com.adamg.materialtemplate.external.SubscriptionManager;
import com.adamg.materialtemplate.presenter.Presenter;

/**
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class ProviderSubscriptionManager extends SubscriptionManager<Presenter> {

    /**
     * Overridden constructor
     *
     * @param instance instance of the BasePresenter
     */
    public ProviderSubscriptionManager(final Presenter instance) {
        super(instance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validate(final Presenter presenter) {
        return !presenter.isDestroyed();
    }
}
