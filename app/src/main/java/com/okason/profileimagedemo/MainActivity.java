package com.okason.profileimagedemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.okason.profileimagedemo.Fragment.CustomerDetailsFragment;
import com.okason.profileimagedemo.Fragment.CustomerListFragment;
import com.okason.profileimagedemo.Fragment.NavigationDrawerFragment;
import com.okason.profileimagedemo.Fragment.OrderDetailsFragment;
import com.okason.profileimagedemo.Fragment.OrderListFragment;
import com.okason.profileimagedemo.Fragment.ProductDetailsFragment;
import com.okason.profileimagedemo.Fragment.ProductListFragment;
import com.okason.profileimagedemo.Helpers.Enums;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment mFragment = null;
        switch (position){
            case 0:
                mFragment = CustomerListFragment.newInstance(0);
                break;
            case 1:
                mFragment = ProductListFragment.newInstance(1);
                break;
            case 2:
                mFragment = OrderListFragment.newInstance(2);
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section0);
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
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

    public void ReplaceFragment(Enums.FragmentEnums frag, int sectionNumber, long itemId){

        NavigationDrawerFragment navFrag = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (frag){
            case CustomerDetailsFragment:
                CustomerDetailsFragment serviceFrag = CustomerDetailsFragment.newInstance(sectionNumber, itemId);
                navFrag.mDrawerToggle.setDrawerIndicatorEnabled(false);
                fragmentManager.beginTransaction().replace(R.id.container, serviceFrag)
                        .addToBackStack(null).commit();
                break;
            case ProductDetailsFragment:
                ProductDetailsFragment profileFrag = ProductDetailsFragment.newInstance(sectionNumber);
                navFrag.mDrawerToggle.setDrawerIndicatorEnabled(false);
                fragmentManager.beginTransaction().replace(R.id.container,profileFrag)
                        .addToBackStack(null).commit();
                break;
            case OrderDetailsFragment:
                OrderDetailsFragment clientFrag = OrderDetailsFragment.newInstance(sectionNumber);
                navFrag.mDrawerToggle.setDrawerIndicatorEnabled(false);
                fragmentManager.beginTransaction().replace(R.id.container,clientFrag)
                        .addToBackStack(null).commit();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavigationDrawerFragment navFrag = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navFrag.mDrawerToggle.setDrawerIndicatorEnabled(true);
        onSectionAttached(navFrag.mCurrentSelectedPosition);
    }


}
