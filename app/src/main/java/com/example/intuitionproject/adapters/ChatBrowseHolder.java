package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ChatBrowseHolder extends RecyclerView.ViewHolder {


    public Context context;

    public ChatBrowseHolder(@NonNull View itemView) {
        super(itemView);

        //userMessage = itemView.findViewById(R.id.text_message_body);
        //name = itemView.findViewById(R.id.userName);

        context = itemView.getContext();

        setListener();
    }

    public void setListener()
    {

    }


}
