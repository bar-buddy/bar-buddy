package com.example.bar_buddy;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements HomeTab.OnFragmentInteractionListener, FavoritesTab.OnFragmentInteractionListener, UpdatesTab.OnFragmentInteractionListener, AccountTab.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;

    private BarCardAdapter adapter;

    private ArrayList<BarItem> bars;

    LockableViewPager viewPager;
    HomeTab homeTab;
    FavoritesTab favoritesTab;
    UpdatesTab updatesTab;
    AccountTab accountTab;

    //Firestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mDrawerLayout = findViewById(R.id.drawer_layout);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        /*viewPager = (LockableViewPager) findViewById(R.id.pager);
        viewPager.setSwipeable(false);
        setupViewPager(viewPager);*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_dashboard:
                    viewPager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_notifications:
                    viewPager.setCurrentItem(2, false);
                    return true;
                case R.id.navigation_account:
                    viewPager.setCurrentItem(3, false);
                    return true;
            }
            return false;
        }
    };
    
    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        homeTab = new HomeTab();
        favoritesTab = new FavoritesTab();
        updatesTab = new UpdatesTab();
        accountTab = new AccountTab();
        pagerAdapter.addFragment(homeTab);
        pagerAdapter.addFragment(favoritesTab);
        pagerAdapter.addFragment(updatesTab);
        pagerAdapter.addFragment(accountTab);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}