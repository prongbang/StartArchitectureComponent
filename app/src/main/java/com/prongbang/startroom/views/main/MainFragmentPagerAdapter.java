package com.prongbang.startroom.views.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.prongbang.startroom.model.Tabs;
import com.prongbang.startroom.views.book.BookFragment;
import com.prongbang.startroom.views.loan.LoanFragment;
import com.prongbang.startroom.views.user.UserFragment;

/**
 * Created by mdev on 10/11/2017 AD.
 */

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGERS = 3;

    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return LoanFragment.newInstance();
            case 1: return BookFragment.newInstance();
            case 2: return UserFragment.newInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return PAGERS;
    }

    // This determines the title for each tab
    @Override
    public CharSequence getPageTitle(int position) {

        return Tabs.generate().get(position).getName();
    }
}
