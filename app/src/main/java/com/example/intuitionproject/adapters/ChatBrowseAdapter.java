package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;

import java.util.List;


public class ChatBrowseAdapter extends RecyclerView.Adapter<ChatBrowseHolder> {

    public static List<String> messages;

    private int USER_MESSAGE = 0, DEVELOPER_MESSAGE = 1;

    private String name;

    @NonNull
    @Override
    public ChatBrowseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        View messageView;

        messageView = inflater.inflate(R.layout.item_chat, parent, false);


       /* if(viewType == USER_MESSAGE) {
            messageView = inflater.inflate(R.layout.item_user_message, parent, false);
        }
        else {
            messageView = inflater.inflate(R.layout.item_developer_message, parent, false);
        }*/

        ChatBrowseHolder chatBrowseHolder = new ChatBrowseHolder(messageView);



        return chatBrowseHolder;
    }






    @Override
    public void onBindViewHolder(@NonNull ChatBrowseHolder holder, int position) {




    }



    @Override
    public int getItemCount() {
        return 5;
    }
}
