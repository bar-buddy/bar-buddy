package com.example.bar_buddy;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class BarDisplayPage extends AppCompatActivity
    implements BarDisplayHomeTab.OnFragmentInteractionListener, BarDisplayMenuTab.OnFragmentInteractionListener, BarDisplayUpdatesTab.OnFragmentInteractionListener {

    ViewPager viewPager;
    BarDisplayHomeTab barDisplayHomeTab;
    BarDisplayMenuTab barDisplayMenuTab;
    BarDisplayUpdatesTab barDisplayUpdatesTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_display_page);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar_display);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager = (ViewPager) findViewById(R.id.pager_bar_display);
        setupViewPager(viewPager);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_bar_display_home:
                    viewPager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_bar_display_menu:
                    viewPager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_bar_display_updates:
                    viewPager.setCurrentItem(2, false);
                    return true;
            }
            return false;
        }
    };

    private void setupViewPager(ViewPager viewPager) {
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        barDisplayHomeTab = new BarDisplayHomeTab();
        barDisplayMenuTab = new BarDisplayMenuTab();
        barDisplayUpdatesTab = new BarDisplayUpdatesTab();
        pagerAdapter.addFragment(barDisplayHomeTab);
        pagerAdapter.addFragment(barDisplayMenuTab);
        pagerAdapter.addFragment(barDisplayUpdatesTab);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
