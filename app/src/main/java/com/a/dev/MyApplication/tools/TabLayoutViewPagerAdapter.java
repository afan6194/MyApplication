package com.a.dev.MyApplication.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ybc on 6/13/17.
 */

public class TabLayoutViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> mFragmentList = new ArrayList<>();
    ArrayList<String> mFragmentTitleList = new ArrayList<>();

    public TabLayoutViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return  mFragmentTitleList.get(position);
    }

    public CharSequence getPageTitleToolbar(int position) {
        return mFragmentTitleList.get(position);
    }
}
