/*
 * Copyright 2014 Prateek Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.adamg.materialtemplate.external;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A class to help manage subscriptions. Subscriptions are automatically observed on the main
 * thread, and it will automatically unsubscribe any subscription if the predicate fails to
 * validate.
 */
public abstract class SubscriptionManager<T> {
    private final T instance;
    private final Func1<T, Boolean> predicate = new Func1<T, Boolean>() {
        @Override
        public Boolean call(T object) {
            return validate(object);
        }
    };
    private final CompositeSubscription subscriptions = new CompositeSubscription();

    public SubscriptionManager(T instance) {
        this.instance = instance;
    }

    public <O> Subscription subscribe(final Observable<O> observable, final Observer<O> observer) {
        final Subscription subscription = observable.subscribe(observer);
        subscriptions.add(subscription);
        return subscription;
    }

    public <O> Subscription subscribeAsync(final Observable<O> observable, final Observer<O> observer) {
        final Subscription subscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(observer);

        subscriptions.add(subscription);
        return subscription;
    }

    public void unsubscribe(Subscription subscription) {
        subscriptions.remove(subscription);
    }

    public void unsubscribeAll() {
        subscriptions.unsubscribe();
    }

    /**
     * called by a {@link Func1} implementation that will be a predicate for {@link
     * rx.android.app.OperatorConditionalBinding}. If the predicate fails to validate, the sequence
     * unsubscribes itself and releases the bound reference.
     */
    protected abstract boolean validate(final T object);

}
