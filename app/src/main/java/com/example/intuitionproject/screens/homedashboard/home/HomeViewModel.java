package com.example.intuitionproject.screens.homedashboard.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.intuitionproject.models.Listing;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    List<Listing> listings;
    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<List<Listing>> getAllListings(){
        final MutableLiveData<List<Listing>> listingObservables = new MutableLiveData<>();
        final ArrayList<Listing> returnList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference requests = db.collection("requests");
        requests.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()) {
                        // process the list
                        Map<String, Object> objectMap = document.getData();
                        returnList.add(
                                new Listing(
                                        objectMap.get("dest-region") == null ? "": objectMap.get("dest-region").toString(),
                                        objectMap.get("details") == null ? "": objectMap.get("details").toString(),
                                        objectMap.get("meetup-region") == null ? "":objectMap.get("meetup-region").toString(),
                                        objectMap.get("payment") == null ? 0: Double.parseDouble(objectMap.get("payment").toString()),
                                        objectMap.get("picture-url") == null ? "":objectMap.get("picture-url").toString(),
                                        objectMap.get("title") == null ? "": objectMap.get("title").toString(),
                                        objectMap.get("userid") == null ? "": objectMap.get("userid").toString(),
                                        Long.parseLong(objectMap.get("timestamp").toString())
                                )
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

    public LiveData<String> getText() {
        return mText;
    }
}