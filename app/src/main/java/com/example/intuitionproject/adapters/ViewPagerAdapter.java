package com.example.intuitionproject.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.intuitionproject.models.Listing;
import com.example.intuitionproject.screens.homedashboard.home.ViewPagerListing;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {
// TODO implement items class
    List<Listing> itemsList;
    public ViewPagerAdapter(FragmentActivity fa, List<Listing> itemsList) {
        super(fa);
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new ViewPagerListing(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}


