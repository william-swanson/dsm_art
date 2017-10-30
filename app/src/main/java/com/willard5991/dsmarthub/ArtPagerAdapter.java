package com.willard5991.dsmarthub;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.MotionEvent;

/**
 * Created by clayton on 10/29/17.
 */

//This is my pageradapter to get the tabs to work
//If you've already done this elsewhere, feel free to remove
public class ArtPagerAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public ArtPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DiscoverFragment tab1 = new DiscoverFragment();
                return tab1;
            case 1:
                DiscoverFragment tab2 = new DiscoverFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}