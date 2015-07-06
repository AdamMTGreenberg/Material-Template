package com.adamg.materialtemplate.ui.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.adamg.materialtemplate.R;
import com.adamg.materialtemplate.presenter.MainActivityPresenter;
import com.adamg.materialtemplate.ui.adapter.NavigationDrawerCallbacks;
import com.adamg.materialtemplate.ui.fragment.NavigationDrawerFragment;
import com.adamg.materialtemplate.ui.views.MainActivityView;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Default setup for the Main Activity - this is the piece that hooks all others for the common
 * execution of the Application layer
 *
 * @author Adam Greenberg
 * @version 1 on 7/5/15
 *          All code under The MIT License (MIT) unless otherwise noted.
 */
public class MainActivity extends BaseActivity implements MainActivityView, NavigationDrawerCallbacks {

    @InjectView(R.id.main_layout_frame_layout)
    FrameLayout mFrameLayout;

    @InjectView(R.id.toolbar_actionbar)
    protected Toolbar mToolbar;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Injected instance of the presenter class for the Main Activity
     */
    @Inject
    MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            mMainActivityPresenter.onFirstCreation();
        }

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com",
                BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()) {
            mNavigationDrawerFragment.closeDrawer();
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.base, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getFrameLayoutId() {
        return R.id.main_layout_frame_layout;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_base_layout;
    }
}
