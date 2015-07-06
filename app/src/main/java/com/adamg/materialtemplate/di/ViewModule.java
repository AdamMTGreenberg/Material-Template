package com.adamg.materialtemplate.di;

import android.app.Activity;

import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.presenter.ActivityPresenterModule;
import com.adamg.materialtemplate.presenter.FragmentPresenterModule;
import com.adamg.materialtemplate.ui.activities.BaseActivity;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *  Base module that provides the View (Activity, Fragment, etc) specific injection pieces
 *
 * @author Adam Greenberg
 * @version 1 on 6/28/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
@Module(
        library = true,
        includes = {
                ActivityPresenterModule.class,
                FragmentPresenterModule.class
        }
)
public class ViewModule {

    private final BaseActivity mActivity;

    public ViewModule(final BaseActivity activity) {
        mActivity= activity;
    }

    /**
     * Allow the activity context to be injected but require that it be annotated with
     * {@link ForActivity @ForActivity} to explicitly differentiate it from application context.
     */
    @Provides
    @ForActivity
    Activity provideActivityContext() {
        return mActivity;
    }

    /**
     * Provides the singular instance of the Navigator, allowing for the setting of the different Views
     */
    @Provides
    Navigator provideNavigator(@ForActivity Activity activityContext) {
        return new Navigator(activityContext);
    }

    @Qualifier
    @Retention(RUNTIME)
    public @interface ForActivity {
    }
}
