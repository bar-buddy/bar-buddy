package com.example.bar_buddy.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.bar_buddy.TabFragments.AccountTab;
import com.example.bar_buddy.Adapters.BarCardAdapter;
import com.example.bar_buddy.AdapterItems.BarItem;
import com.example.bar_buddy.TabFragments.FavoritesTab;
import com.example.bar_buddy.TabFragments.HomeTab;
import com.example.bar_buddy.LockableViewPager;
import com.example.bar_buddy.Adapters.PagerAdapter;
import com.example.bar_buddy.R;
import com.example.bar_buddy.TabFragments.UpdatesTab;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
    implements HomeTab.OnFragmentInteractionListener, FavoritesTab.OnFragmentInteractionListener, UpdatesTab.OnFragmentInteractionListener, AccountTab.OnFragmentInteractionListener {

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
        viewPager = (LockableViewPager) findViewById(R.id.pager);
        viewPager.setSwipeable(false);
        setupViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        //actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
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