package com.example.intuitionproject.screens.homedashboard.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.intuitionproject.adapters.CubeOutPageTransformer;
import com.example.intuitionproject.adapters.ViewPagerAdapter;
import com.example.intuitionproject.databinding.FragmentHomeBinding;
import com.example.intuitionproject.models.Listing;
import com.example.intuitionproject.screens.ChatScreen;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    FragmentHomeBinding binding;
    private Listing selectedItem;
    ViewPagerAdapter adapter;
    public HomeFragment() {

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeViewModel.getAllListings().observe(getViewLifecycleOwner(), new Observer<List<Listing>>() {
            @Override
            public void onChanged(final List<Listing> listings) {
                adapter = new ViewPagerAdapter(getActivity(), listings);
                binding.viewPager.setAdapter(adapter);
                binding.viewPager.setPageTransformer(new CubeOutPageTransformer());

                // register changes in viewpager so we can change the ui
                binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }

                    @Override
                    public void onPageSelected(final int position) {
                        super.onPageSelected(position);
                        setBottomSheet(listings.get(position));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });
                binding.designBottomSheet.expandMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BottomSheetBehavior.from(binding.designBottomSheet.getRoot()).setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                });
            }
        });


//
    }

    public void setBottomSheet(final Listing item){
        binding.designBottomSheet.itemDesc.setText(item.getDetails());
        binding.designBottomSheet.itemMeetupLocation.setText(item.getMeetupRegion());
        binding.designBottomSheet.itemDestinationLocation.setText(item.getDestinationRegion());
        binding.designBottomSheet.itemPayoutAmount.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(item.getPaymentAmount()));
        binding.designBottomSheet.itemTitle.setText(item.getTitle());
        if(item.getChatId() != null){
            // if chat id eixsts, open chat activity with it
            binding.designBottomSheet.chatWithButton.setText("View Chat");
            binding.designBottomSheet.chatWithButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(requireActivity(), ChatScreen.class);
                    intent.putExtra("id", item.getChatId());
                    startActivity(intent);
                }
            });
        }else{
            binding.designBottomSheet.chatWithButton.setText("Chat");
            binding.designBottomSheet.chatWithButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    homeViewModel.createNewChat(item.getDocumentId()).observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            // open activity with new id
                            Intent intent = new Intent(requireActivity(), ChatScreen.class);
                            intent.putExtra("id", s);
                            startActivity(intent);
                        }
                    });

                }
            });
        }
    }

}