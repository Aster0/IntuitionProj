package com.example.intuitionproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;
import com.example.intuitionproject.screens.ChatScreen;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private static List<String> messages;

    public static List<String> getMessages()
    {
        return messages;
    }
    public static void setMessages(List<String> array) { messages = array; }

    private int USER_MESSAGE = 0, RECEIVER_MESSAGE = 1;

    private String name;

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        View messageView;




        if(viewType == USER_MESSAGE) {
            messageView = inflater.inflate(R.layout.item_logged_user_message, parent, false);
        }
        else {
            messageView = inflater.inflate(R.layout.item_receiving_user_message, parent, false);
        }

        MessageHolder messageHolder = new MessageHolder(messageView);



        return messageHolder;
    }



    @Override
    public int getItemViewType(int position) {

        System.out.println(position + " POSITION");

        String message = messages.get(position);


        if(message.startsWith("s}"))
        {

            return USER_MESSAGE;
        }
        else
        {

            return RECEIVER_MESSAGE;
        }


    }


    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

        String message = messages.get(position);
        holder.userMessage.setText(message.replace("s}", "").replace("r}", ""));


        String name;
        if(messages.get(position).startsWith("s}"))
            name = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        else
            name = ChatScreen.getReceiverName();

        holder.name.setText(name);


    }



    @Override
    public int getItemCount() {
        System.out.println( messages.size());
        return messages.size();
    }
}
