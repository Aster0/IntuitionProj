package com.example.intuitionproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
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
import java.util.List;
import java.util.Map;

public class RequestActivity extends AppCompatActivity {

    private ActivityRequestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.makeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestActivity.this, newRequestActivity.class);
                startActivity(intent);
            }
        });

        getAllListings().observe(this, new Observer<List<Listing>>() {
            @Override
            public void onChanged(List<Listing> listings) {
                binding.requestListView.setAdapter(new RequestListAdapter(listings));
                binding.requestListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.requestListView.getAdapter().notifyDataSetChanged();
            }
        });
    }

    public LiveData<List<Listing>> getAllListings () {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final MutableLiveData<List<Listing>> listingObservables = new MutableLiveData<>();
        final ArrayList<Listing> returnList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference requests = db.collection("requests");
        requests.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (!document.get("userid").equals(user.getEmail())) continue;
                        // process the list
                        Map<String, Object> objectMap = document.getData();
                        returnList.add(
                                new Listing(
                                        objectMap.get("dest-region") == null ? "" : objectMap.get("dest-region").toString(),
                                        objectMap.get("details") == null ? "" : objectMap.get("details").toString(),
                                        objectMap.get("meetup-region") == null ? "" : objectMap.get("meetup-region").toString(),
                                        objectMap.get("payment") == null ? 0 : Double.parseDouble(objectMap.get("payment").toString()),
                                        objectMap.get("picture-url") == null ? "" : objectMap.get("picture-url").toString(),
                                        objectMap.get("title") == null ? "" : objectMap.get("title").toString(),
                                        objectMap.get("userid") == null ? "" : objectMap.get("userid").toString(),
                                        Long.parseLong(objectMap.get("timestamp").toString()),
                                        document.getId())
                        );}
                    Log.e("returnlist", String.valueOf(returnList.size()));
                    // tell observers we are done
                    listingObservables.postValue(returnList);
                }
            }
        });
        return listingObservables;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllListings().observe(this, new Observer<List<Listing>>() {
            @Override
            public void onChanged(List<Listing> listings) {
                binding.requestListView.setAdapter(new RequestListAdapter(listings));
                binding.requestListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                binding.requestListView.getAdapter().notifyDataSetChanged();
            }
        });
    }
}