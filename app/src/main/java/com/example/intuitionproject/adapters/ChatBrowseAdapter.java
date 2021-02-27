package com.example.intuitionproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.R;
import com.example.intuitionproject.screens.ChatScreen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ChatBrowseAdapter extends RecyclerView.Adapter<ChatBrowseHolder> {

    private static List<DocumentSnapshot> chats;

    public static List<DocumentSnapshot> getChats()
    {
        return chats;
    }

    public static void setChats(List<DocumentSnapshot> array) { chats = array; }

    private final int USER_MESSAGE = 0;
    private final int DEVELOPER_MESSAGE = 1;

    private String name;

    @NonNull
    @Override
    public ChatBrowseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);



        View messageView;

        messageView = inflater.inflate(R.layout.item_chat, parent, false);



        ChatBrowseHolder chatBrowseHolder = new ChatBrowseHolder(messageView);



        return chatBrowseHolder;
    }






    @Override
    public void onBindViewHolder(@NonNull final ChatBrowseHolder holder, int position) {
        System.out.println(chats);
        final DocumentSnapshot documentSnapshot = chats.get(position);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatID = documentSnapshot.getId();

                Intent intent = new Intent(holder.context, ChatScreen.class);
                System.out.println(chatID);
                intent.putExtra("id", chatID);

                holder.context.startActivity(intent);
            }
        });

        final String[] title = new String[1];
        String username = null;


        String target = documentSnapshot.get("target").toString();
        username = documentSnapshot.get("username").toString();

        final String finalUsername = username;
        FirebaseFirestore.getInstance().collection("requests").document(target)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot1) {
                title[0] = documentSnapshot1.get("title").toString();

                String username = documentSnapshot1.get("userid").toString();
                if(documentSnapshot1.get("userid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                {
                    username = documentSnapshot.get("username").toString();
                }


                holder.chatTitle.setText(title[0]);
                holder.username.setText(username);
                List<String> replies = (List<String>)documentSnapshot.getData().get("replies");
                holder.latestMessage.setText(replies.get(replies.size()-1).substring(2));
                Picasso.get().load(documentSnapshot1.get("picture-url").toString()).into(holder.chatImage);
            }
        });

            










    }



    @Override
    public int getItemCount() {
        return chats.size();
    }
}
