package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;


public class ChatBrowseHolder extends RecyclerView.ViewHolder {

    public TextView chatTitle;
    public TextView username;
    public ImageView chatImage;
    public ConstraintLayout constraintLayout;


    public Context context;

    public ChatBrowseHolder(@NonNull View itemView) {
        super(itemView);

        //userMessage = itemView.findViewById(R.id.text_message_body);
        //name = itemView.findViewById(R.id.userName);

        chatTitle = itemView.findViewById(R.id.chatTitle);
        username = itemView.findViewById(R.id.chatUsername);
        chatImage = itemView.findViewById(R.id.image_message_profile);
        constraintLayout = itemView.findViewById(R.id.chat);

        context = itemView.getContext();

        setListener();
    }

    public void setListener()
    {

    }


}
