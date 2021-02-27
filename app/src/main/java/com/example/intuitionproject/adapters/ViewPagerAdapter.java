package com.example.intuitionproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
// TODO implement items class
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Integer> itemsList) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //TODO instantiate our fragment
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
