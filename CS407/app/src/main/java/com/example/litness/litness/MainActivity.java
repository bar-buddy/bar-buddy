package com.example.litness.litness;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.litness.litness.Adapter.BarCardAdapter;
import com.example.litness.litness.Dialog.LoginDialog;
import com.example.litness.litness.Dialog.YesNoDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private BarCardAdapter adapter;

    private Menu searchMenu;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String query = "";

    private String sort = "D";

    private SwipeRefreshLayout swipeContainer;

    private final List<String> activeFilters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView rvCards = findViewById(R.id.main_card_rv);
        adapter = new BarCardAdapter(this);
        rvCards.setAdapter(adapter);

        swipeContainer = findViewById(R.id.main_sr);
        swipeContainer.setOnRefreshListener(this::actionSwipeRefresh);

        populateBars();

    }

    @Override
    public void onResume() {
        super.onResume();
        initMenuDrawer();
        updateBars();
    }

    private void populateBars() {
        activeFilters.add("All Bars");
        updateBars();
        LinearLayout ll = findViewById(R.id.main_container_filters);
        for(String s : getResources().getStringArray(R.array.filter_options)){
            int layout, hiddenLayout;
            if(s.equals("All Bars")){
                layout = R.layout.adapter_filter_on;
                hiddenLayout = R.layout.adapter_filter_off;
            }
            else{
                layout = R.layout.adapter_filter_off;
                hiddenLayout = R.layout.adapter_filter_on;
            }

            View v = getLayoutInflater().inflate(layout, null, false);
            View w = getLayoutInflater().inflate(hiddenLayout, null, false);

            ((TextView) v.findViewById(R.id.adapter_alt_filter)).setText(s);
            ((TextView) w.findViewById(R.id.adapter_alt_filter)).setText(s);

            w.findViewById(R.id.adapter_container).setVisibility(View.GONE);

            v.findViewById(R.id.adapter_container).setOnClickListener(x->{ // OFF to ON
                v.findViewById(R.id.adapter_container).setVisibility(View.GONE);
                w.findViewById(R.id.adapter_container).setVisibility(View.VISIBLE);
                if(s.equals("All Bars"))
                    activeFilters.remove(s);
                else
                    activeFilters.add(s);

                updateBars();

            });

            w.findViewById(R.id.adapter_container).setOnClickListener(x->{ //ON to OFF
                w.findViewById(R.id.adapter_container).setVisibility(View.GONE);
                v.findViewById(R.id.adapter_container).setVisibility(View.VISIBLE);
                if(s.equals("All Bars"))
                    activeFilters.add(s);
                else
                    activeFilters.remove(s);
                updateBars();
            });

            ll.addView(v);
            ll.addView(w);
        }
    }

    private void updateBars(){
        Set<String> bs = Client.barMap.keySet();
        List<Bar> filtered = new ArrayList<>();
        for(String s : bs){
            Bar b = Client.barMap.get(s);
            boolean add = true;
            for(String f : activeFilters){
                if(!(b.tags.contains(f) && !filtered.contains(b)))
                    add = false;
            }
            //search on bar name if you want
            if(add && b.barName.toLowerCase().contains(query.toLowerCase()))
                filtered.add(b);
        }
        switch (sort) {
            case "AZ":
                Collections.sort(filtered, (b1, b2) -> b1.barName.compareTo(b2.barName));
                break;
            case "ZA":
                Collections.sort(filtered, (b1, b2) -> b2.barName.compareTo(b1.barName));
                break;
            case "LitLH":
                Collections.sort(filtered, (b1, b2) -> b1.litness.compareTo(b2.litness));
                break;
            case "LitHL":
                Collections.sort(filtered, (b1, b2) -> b2.litness.compareTo(b1.litness));
                break;
            case "CovLH":
                Collections.sort(filtered, (b1, b2) -> b2.coverOver.compareTo(b1.coverOver));
                break;
            case "CovHL":
                Collections.sort(filtered, (b1, b2) -> b1.coverOver.compareTo(b2.coverOver));
                break;
        }
            adapter.updateBars(filtered);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate sort menu
        getMenuInflater().inflate(R.menu.sort_menu, menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.input_filter);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);

        //fixes all the colors for searching with the toolbar
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.Primary));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.Primary));

        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_search_24dp);
        ImageView closeIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeIcon.setImageResource(R.drawable.ic_close_primary_24dp);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = q;
                updateBars();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String q) {
                query = q;
                updateBars();
                return false;
            }
        });
        searchView.setOnCloseListener( () -> false);
        return true;
    }

    private void actionSwipeRefresh() {
        swipeContainer.setRefreshing(true); {
            updateBars();
        }
        if (swipeContainer.isRefreshing())
            swipeContainer.setRefreshing(false);
    }

    //Handles if the nav drawerLayout button is pressed
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item))
            return true;

        int id = item.getItemId();
        switch (id) {
            case R.id.sortmenu_default:
                item.setChecked(true);
                sort = "D";
                updateBars();
                break;
            case R.id.sortmenu_AZ:
                item.setChecked(true);
                sort = "AZ";
                updateBars();
                break;
            case R.id.sortmenu_ZA:
                item.setChecked(true);
                sort = "ZA";
                updateBars();
                break;
            case R.id.sortmenu_LitHL:
                item.setChecked(true);
                sort = "LitHL";
                updateBars();
                break;
            case R.id.sortmenu_LitLH:
                item.setChecked(true);
                sort = "LitLH";
                updateBars();
                break;
            case R.id.sortmenu_CovHL:
                item.setChecked(true);
                sort = "CovHL";
                updateBars();
                break;
            case R.id.sortmenu_covLH:
                item.setChecked(true);
                sort = "CovLH";
                updateBars();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void initMenuDrawer() {
        drawerLayout = findViewById(R.id.drawer_main);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.login, R.string.logout);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        ActionBar actionbar = getSupportActionBar();
        Objects.requireNonNull(actionbar).setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        NavigationView nv = findViewById(R.id.nav_main);
        View header = nv.getHeaderView(0);

        searchMenu = nv.getMenu();

        //sets the header view
        if(Client.currentUserName.equals("")) {
            header.findViewById(R.id.maindrawer_view).setVisibility(View.INVISIBLE);
            searchMenu.findItem(R.id.menuLoginLogout).setTitle("Login");
        }
        else {
            header.findViewById(R.id.maindrawer_view).setVisibility(View.VISIBLE);
            ((TextView) header.findViewById(R.id.maindrawer_name)).setText(Client.currentUserName);
            searchMenu.findItem(R.id.menuLoginLogout).setTitle("Logout");
        }

        nv.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menuLoginLogout) {
                if(Client.currentUserName.equals("")) {
                    new LoginDialog(this, x->{
                        Client.currentUserName = x;
                        searchMenu.findItem(R.id.menuLoginLogout).setTitle("Logout");
                        findViewById(R.id.maindrawer_view).setVisibility(View.VISIBLE);
                        ((TextView)findViewById(R.id.maindrawer_name)).setText(x);
                    }).show();
                }
                //logout must have been shown so reset current username
                else {
                    new YesNoDialog(this,"Are you sure you want to Logout?", "", new Interface.YesNoHandler() {
                        @Override
                        public void onYesClicked() {
                            deleteLoginInfo();
                            findViewById(R.id.maindrawer_view).setVisibility(View.INVISIBLE);
                            searchMenu.findItem(R.id.menuLoginLogout).setTitle("Login");
                        }
                        @Override
                        public void onNoClicked() {

                        }
                    }).show();
                }
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(Gravity.START);
        else
            super.onBackPressed();
    }

    private void deleteLoginInfo() {
        Client.currentUserName = "";
        //clear the shared preferences
        getSharedPreferences("Login", MODE_PRIVATE).edit().clear().apply();

        //remove the file
        //noinspection ResultOfMethodCallIgnored
        new File(getApplicationInfo().dataDir + "/shared_prefs/Login.xml").delete();
    }
}
