package com.flavours5.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flavours5.ui.category.BlankFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentId = new ArrayList<>();
    private FragmentManager manager;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        manager = fm;
    }


    @Override
    public Fragment getItem(int position) {
        return BlankFragment.newInstance( mFragmentId.get(position)) ;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    void addFrag(Fragment fragment, String title, String id) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentId.add(Integer.parseInt(id));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
