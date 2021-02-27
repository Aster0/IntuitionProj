package com.example.intuitionproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.intuitionproject.databinding.ActivityRequestBinding;
import com.example.intuitionproject.databinding.ContentNewRequestActivityBinding;

public class RequestActivity extends AppCompatActivity {

    private ActivityRequestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}