package com.adamg.materialtemplate.presenter;

/**
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 * All code under The MIT License (MIT) unless otherwise noted.
 */

import android.app.Activity;

import com.adamg.materialtemplate.di.ViewModule;
import com.adamg.materialtemplate.navigator.Navigator;
import com.adamg.materialtemplate.ui.fragment.FirstFragment;
import com.adamg.materialtemplate.ui.views.SecondFragment;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = {
                FirstFragment.class,
                SecondFragment.class
        },
        library = true,
        complete = false
)
public class FragmentPresenterModule {

        @Provides
        FirstFragmentPresenter provideFirstFragmentPresenter(@ViewModule.ForActivity Activity context,
                                                             Navigator navigator) {
                return new FirstFragmentPresenterImpl(context, navigator);
        }

        @Provides
        SecondFragmentPresenter provideSecondFragmentPresenter(@ViewModule.ForActivity Activity context,
                                                             Navigator navigator) {
                return new SecondFragmentPresenterImpl(context, navigator);
        }
}
