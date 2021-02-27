package com.example.intuitionproject.screens.homedashboard.requests;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.intuitionproject.RequestListAdapter;
import com.example.intuitionproject.databinding.ActivityRequestBinding;
import com.example.intuitionproject.models.Listing;
import com.example.intuitionproject.newRequestActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestsFragment extends Fragment {

    private ActivityRequestBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityRequestBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.newFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), newRequestActivity.class);
                startActivity(intent);
            }
        });

        getAllListings().observe(getViewLifecycleOwner(), new Observer<List<Listing>>() {
            @Override
            public void onChanged(List<Listing> listings) {
                binding.requestListView.setAdapter(new RequestListAdapter(listings));
                binding.requestListView.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.requestListView.getAdapter().notifyDataSetChanged();
                // fancy fab action
                binding.requestListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                        if (dy > 0)
                            binding.newFab.hide();
                        else if (dy < 0)
                            binding.newFab.show();
                    }
                });
            }
        });
    }

    public LiveData<List<Listing>> getAllListings() {
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
                                        document.getId(), null, objectMap.get("accepted-by").toString())
                        );
                    }
                    Log.e("returnlist", String.valueOf(returnList.size()));
                    // tell observers we are done
                    listingObservables.postValue(returnList);
                }
            }
        });
        return listingObservables;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllListings().observe(this, new Observer<List<Listing>>() {
            @Override
            public void onChanged(List<Listing> listings) {
                binding.requestListView.setAdapter(new RequestListAdapter(listings));
                binding.requestListView.setLayoutManager(new LinearLayoutManager(requireContext()));
                binding.requestListView.getAdapter().notifyDataSetChanged();
            }
        });
    }
}