package com.example.intuitionproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.intuitionproject.screens.ChatBrowse;
import com.example.intuitionproject.screens.ChatScreen;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<String, String> test = new HashMap<>();

        test.put("test", "testtt");

        FirebaseFirestore.getInstance().collection("testing").document("test").set(test);
        //test commit - Jordy


        Intent intent = new Intent(this, ChatScreen.class);

        startActivity(intent);
    }
}