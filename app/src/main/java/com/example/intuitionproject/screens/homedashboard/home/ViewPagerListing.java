package com.example.intuitionproject.screens.homedashboard.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intuitionproject.databinding.ViewPagerListingFragmentBinding;
import com.example.intuitionproject.models.Listing;
import com.squareup.picasso.Picasso;

public class ViewPagerListing extends Fragment {

    private ViewPagerListingViewModel mViewModel;
    ViewPagerListingFragmentBinding binding;
    final Listing listing;
    public ViewPagerListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ViewPagerListingFragmentBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(listing != null){
            Picasso.get()
                    .load(listing.getPictureUrl())
                    .into(binding.imageView);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ViewPagerListingViewModel.class);
        mViewModel.listing = listing;
    }

}