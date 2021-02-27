package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;
import com.google.android.material.textview.MaterialTextView;


public class ChatBrowseHolder extends RecyclerView.ViewHolder {

    public final TextView chatTitle;
    public final TextView username;
    public final MaterialTextView latestMessage;
    public final ImageView chatImage;
    public final ConstraintLayout constraintLayout;


    public final Context context;

    public ChatBrowseHolder(@NonNull View itemView) {
        super(itemView);

        //userMessage = itemView.findViewById(R.id.text_message_body);
        //name = itemView.findViewById(R.id.userName);

        chatTitle = itemView.findViewById(R.id.chatTitle);
        username = itemView.findViewById(R.id.chatUsername);
        chatImage = itemView.findViewById(R.id.image_message_profile);
        constraintLayout = itemView.findViewById(R.id.chat);
        latestMessage = itemView.findViewById(R.id.latest_msg);
        context = itemView.getContext();

        setListener();
    }

    public void setListener()
    {

    }


}
