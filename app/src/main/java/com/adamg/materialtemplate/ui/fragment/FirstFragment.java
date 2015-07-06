package com.adamg.materialtemplate.ui.fragment;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.adamg.materialtemplate.R;
import com.adamg.materialtemplate.presenter.FirstFragmentPresenter;
import com.adamg.materialtemplate.presenter.FragmentPresenter;
import com.adamg.materialtemplate.ui.views.FirstFragmentView;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class FirstFragment extends BaseFragment implements FirstFragmentView {

    @Inject
    FirstFragmentPresenter mPresenter;

    @InjectView(R.id.fragment_first_fragment_btn)
    Button mBtn;


    public static Fragment newInstance() {
        return new FirstFragment();
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_first_layout;
    }

    @NonNull
    @Override
    protected FragmentPresenter getPresenter() {
        return mPresenter;
    }

    @OnClick(R.id.fragment_first_fragment_btn)
    public void onSecondFragmentClick(final View view) {
        mPresenter.onSecondFragmentClick();
    }
}
