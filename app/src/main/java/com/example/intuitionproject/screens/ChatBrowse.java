package com.example.intuitionproject.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.intuitionproject.R;
import com.example.intuitionproject.adapters.ChatBrowseAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatBrowse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_browse);

        buildChats();


    }

    private void buildRecycledView()
    {
        RecyclerView recyclerView = findViewById(R.id.chatBrowseRecycler);

        ChatBrowseAdapter chatBrowseAdapter = new ChatBrowseAdapter();
        recyclerView.setAdapter(chatBrowseAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(ChatBrowse.this));
    }


    private void buildChats()
    {

        List<DocumentSnapshot> list = new ArrayList<>();
        ChatBrowseAdapter.setChats(list);
        FirebaseFirestore.getInstance().collection("requests").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                for(final DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                {
                    if(documentSnapshot.get("userid").equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                    {
                        FirebaseFirestore.getInstance().collection("chats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                                for(DocumentSnapshot documentSnapshot1 : queryDocumentSnapshots.getDocuments())
                                {
                                    System.out.println(documentSnapshot1);
                                    if(documentSnapshot1.get("target").equals(documentSnapshot.getId()))
                                    {

                                        System.out.println(documentSnapshot1);
                                        ChatBrowseAdapter.getChats().add(documentSnapshot1);
                                        buildRecycledView();

                                    }
                                }
                            }
                        });
                    }





                }


                FirebaseFirestore.getInstance().collection("chats").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments())
                        {
                            if(documentSnapshot.get("username").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                            {
                                ChatBrowseAdapter.getChats().add(documentSnapshot);
                                buildRecycledView();
                            }
                        }
                    }
                });

            }
        });
    }


}