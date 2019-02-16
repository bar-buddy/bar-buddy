package com.example.bar_buddy;

<<<<<<< HEAD
import android.net.Uri;
=======
>>>>>>> 6d29ccf0e06a6053092e19d5a33b7d9c0a321d07
import android.content.Intent;
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
import android.widget.Button;
<<<<<<< HEAD
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
    implements HomeTab.OnFragmentInteractionListener, FavoritesTab.OnFragmentInteractionListener, UpdatesTab.OnFragmentInteractionListener, AccountTab.OnFragmentInteractionListener{
=======
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
>>>>>>> 6d29ccf0e06a6053092e19d5a33b7d9c0a321d07

    private DrawerLayout mDrawerLayout;

    ViewPager viewPager;
    HomeTab homeTab;
    FavoritesTab favoritesTab;
    UpdatesTab updatesTab;
    AccountTab accountTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

<<<<<<< HEAD
=======
        final Button btnLogout = findViewById(R.id.logoutBtn);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutOnClick(v);
            }
        });

>>>>>>> 6d29ccf0e06a6053092e19d5a33b7d9c0a321d07
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

<<<<<<< HEAD
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
=======
    public void logoutOnClick(View v){
        if (v.getId() == R.id.logoutBtn){
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
        }
    }
>>>>>>> 6d29ccf0e06a6053092e19d5a33b7d9c0a321d07

    }
}