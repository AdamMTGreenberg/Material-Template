package com.adamg.materialtemplate.ui.activities;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adamg.materialtemplate.BaseApplication;
import com.adamg.materialtemplate.R;
import com.adamg.materialtemplate.di.Injector;
import com.adamg.materialtemplate.di.ViewModule;
import com.adamg.materialtemplate.ui.adapter.NavigationDrawerCallbacks;
import com.adamg.materialtemplate.ui.fragment.NavigationDrawerFragment;
import com.adamg.materialtemplate.ui.views.View;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import dagger.ObjectGraph;
import rx.android.internal.Preconditions;


public abstract class BaseActivity extends AppCompatActivity implements View, NavigationDrawerCallbacks,
        Injector {

    /**
     * Activity scope graph use to manage the DI pieces of Dagger
     */
    private ObjectGraph mActivityScopeGraph;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    protected NavigationDrawerFragment mNavigationDrawerFragment;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        // Create a new Dagger ObjectGraph
        injectDependencies();

        // Inject all annotated Views
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        // populate the navigation drawer
        mNavigationDrawerFragment.setUserData("John Doe", "johndoe@doe.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));

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
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        mActivityScopeGraph = null;

        super.onDestroy();
    }

    /**
     * Gets this Activity's object graph.
     *
     * @return the object graph
     */
    @Override
    public final ObjectGraph getObjectGraph() {
        return mActivityScopeGraph;
    }

    /**
     * Inject the supplied {@code object} using the activity-specific graph.
     */
    @Override
    public void inject(final @NonNull Object object) {
        Preconditions.checkState(mActivityScopeGraph != null, "object graph must be assigned prior to calling inject");
        mActivityScopeGraph.inject(object);
    }

    /**
     * Obtains a reference to the FrameLayout used for Fragment swapping
     *
     * @return Layout resource ID for the FrameLayout housing the fragments
     */
    @IdRes
    public abstract int getFrameLayoutId();

    /**
     * Obtains a reference to the Layout for injection via ButterKnife
     *
     * @return Layout resource ID for the layout
     */
    @LayoutRes
    public abstract int getLayout();

    /**
     * Get a list of Dagger modules with Activity scope needed to this Activity.
     *
     * @return modules with new dependencies to provide.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ViewModule(this));
    }

    /**
     * Create a new Dagger ObjectGraph to add new dependencies using a plus operation and inject the
     * declared one in the activity. This new graph will be destroyed once the activity lifecycle
     * finish.
     * <p/>
     * This is the key of how to use Activity scope dependency injection.
     */
    private void injectDependencies() {
        // Get a reference to the Application
        BaseApplication application = (BaseApplication) getApplication();
        mActivityScopeGraph = application.getApplicationGraph().plus(getModules().toArray());
        // Inject ourselves so subclasses will have dependencies fulfilled when this method returns.
        inject(this);
    }


}
