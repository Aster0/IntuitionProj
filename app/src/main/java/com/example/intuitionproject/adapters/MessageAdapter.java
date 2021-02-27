package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    public static List<String> messages;

    private int USER_MESSAGE = 0, DEVELOPER_MESSAGE = 1;

    private String name;

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        View messageView;


        if(viewType == USER_MESSAGE) {
            messageView = inflater.inflate(R.layout.item_receiving_user_message, parent, false);
        }
        else {
            messageView = inflater.inflate(R.layout.item_logged_user_message, parent, false);
        }

        MessageHolder messageHolder = new MessageHolder(messageView);



        return messageHolder;
    }



    @Override
    public int getItemViewType(int position) {

        System.out.println(position + " POSITION");

//        String message = messages.get(position);


        /*if(message.startsWith("u}"))
        {
            name = UserProfile.getUser().getFullName();
            return USER_MESSAGE;
        }
        else
        {
            name = "Developer";
            return DEVELOPER_MESSAGE;
        }*/
        return 0;
    }


    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

       // String message = messages.get(position);
        holder.userMessage.setText("hi");

        holder.name.setText("John Doe");


    }



    @Override
    public int getItemCount() {
        return 5;
    }
}
