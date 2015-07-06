package com.adamg.materialtemplate.ui.views;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.adamg.materialtemplate.R;
import com.adamg.materialtemplate.presenter.FragmentPresenter;
import com.adamg.materialtemplate.presenter.SecondFragmentPresenter;
import com.adamg.materialtemplate.ui.fragment.BaseFragment;

import javax.inject.Inject;

/**
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class SecondFragment extends BaseFragment implements SecondFragmentView {

    @Inject
    SecondFragmentPresenter mPresenter;

    public static Fragment newInstance() {
        return new SecondFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_second_layout;
    }

    @NonNull
    @Override
    protected FragmentPresenter getPresenter() {
        return mPresenter;
    }
}
