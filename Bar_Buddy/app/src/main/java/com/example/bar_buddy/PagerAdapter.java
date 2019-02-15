package com.example.bar_buddy;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs) {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                HomeTab homeTab = new HomeTab();
                return homeTab;
            case 1:
                FavoritesTab favoritesTab = new FavoritesTab();
                return favoritesTab;
            case 2:
                UpdatesTab updatesTab = new UpdatesTab();
                return updatesTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
