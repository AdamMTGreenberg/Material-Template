package com.adamg.materialtemplate.presenter;

import android.app.Activity;

import com.adamg.materialtemplate.di.ViewModule;
import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.ui.activities.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
@Module(
        injects = {
                // Add all View pieces
                MainActivity.class
        },
        complete = false,
        library = true
)
public class ActivityPresenterModule {

    @Provides
    MainActivityPresenter provideMainActivityPresenter(@ViewModule.ForActivity Activity act, Navigator navigator) {
        return new MainActivityPresenterImpl(act, navigator);
    }
}
