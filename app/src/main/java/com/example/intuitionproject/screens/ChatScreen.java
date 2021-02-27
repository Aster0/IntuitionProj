package com.example.intuitionproject.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.intuitionproject.R;
import com.example.intuitionproject.adapters.ChatBrowseAdapter;
import com.example.intuitionproject.adapters.MessageAdapter;

public class ChatScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);


        RecyclerView recyclerView = findViewById(R.id.chatRecycledView);

        MessageAdapter messageAdapter = new MessageAdapter();
        recyclerView.setAdapter(messageAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ChatScreen.this));
    }
}