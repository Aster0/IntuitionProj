package com.example.intuitionproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.intuitionproject.databinding.ActivityRequestBinding;
import com.example.intuitionproject.databinding.ContentNewRequestActivityBinding;
import com.example.intuitionproject.models.Listing;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    private ActivityRequestBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        RefreshDatabase();

        binding.makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestActivity.this, newRequestActivity.class);
                startActivity(intent);
            }
        });
    }

    private void RefreshDatabase() {
        FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore storage = FirebaseFirestore.getInstance();

        final CollectionReference docRef = storage.collection("requests");

        Query requestQuery = docRef.whereEqualTo("userid", auth.getEmail());

        requestQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Listing> listingData = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("lol", document.getId() + " => " + document.getData());

                        String picture_url = document.get("picture-url").toString();

                        String destination_region = document.get("dest-region").toString();
                        String meetup_region = document.get("meetup-region").toString();
                        double payment = Double.parseDouble(document.get("payment").toString());
                        String title = document.get("title").toString();
                        String details = document.get("details").toString();
                        String userid = document.get("userid").toString();
                        long time = Long.parseLong(document.get("timestamp").toString());
                        String documentId = document.getId();
                        listingData.add(new Listing(destination_region, details, meetup_region, payment, picture_url, title, userid, time, documentId));
                    }
                    binding.requestListView.setAdapter(new RequestListAdapter(listingData));
                    binding.requestListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    binding.requestListView.getAdapter().notifyDataSetChanged();
                } else {
                    Log.d("error", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( binding.requestListView.getAdapter() != null)
            RefreshDatabase();

    }
}