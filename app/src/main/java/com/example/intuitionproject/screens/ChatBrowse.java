package com.example.intuitionproject.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.intuitionproject.R;
import com.example.intuitionproject.adapters.ChatBrowseAdapter;

public class ChatBrowse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_browse);

        RecyclerView recyclerView = findViewById(R.id.chatBrowseRecycler);

        ChatBrowseAdapter chatBrowseAdapter = new ChatBrowseAdapter();
        recyclerView.setAdapter(chatBrowseAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ChatBrowse.this));
    }


    private void buildChats()
    {

    }
}