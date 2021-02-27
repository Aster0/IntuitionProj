package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;



public class MessageHolder extends RecyclerView.ViewHolder {

    public TextView userMessage;
    public TextView name;
    public Context context;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);

        userMessage = itemView.findViewById(R.id.text_message_body);
        name = itemView.findViewById(R.id.userName);

        context = itemView.getContext();

        setListener();
    }

    public void setListener()
    {
        userMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(getAdapterPosition() + " POS");
            }
        });
    }


}
