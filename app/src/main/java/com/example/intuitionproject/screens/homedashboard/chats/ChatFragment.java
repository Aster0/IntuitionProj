package com.example.intuitionproject.screens.homedashboard.chats;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.intuitionproject.R;
import com.example.intuitionproject.adapters.ChatBrowseAdapter;
import com.example.intuitionproject.databinding.ActivityChatBrowseBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    ActivityChatBrowseBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityChatBrowseBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildChats();


    }

    private void buildRecycledView()
    {
        RecyclerView recyclerView = requireActivity().findViewById(R.id.chatBrowseRecycler);

        ChatBrowseAdapter chatBrowseAdapter = new ChatBrowseAdapter();
        recyclerView.setAdapter(chatBrowseAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void buildChats()
    {

        List<DocumentSnapshot> list = new ArrayList<>();
        ChatBrowseAdapter.setChats(list);
        FirebaseFirestore.getInstance().collection("requests").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                final List<DocumentSnapshot> requests = queryDocumentSnapshots.getDocuments();

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
                                boolean found = false;
                                for(DocumentSnapshot request : requests)
                                {
                                    if(request.getId().equals(documentSnapshot.get("target")))
                                    {
                                        found = true;
                                    }
                                }

                                if(found)
                                {
                                    ChatBrowseAdapter.getChats().add(documentSnapshot);
                                    buildRecycledView();
                                }

                            }
                        }
                    }
                });

            }
        });
    }


}