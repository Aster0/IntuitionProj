package com.example.intuitionproject;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.intuitionproject.models.Listing;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.ViewHolder> {

    private ArrayList<Listing> listings;

    public RequestListAdapter (ArrayList<Listing> listings) {
        this.listings = listings;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.request_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Listing listing = listings.get(position);
        holder.requestName.setText(listing.getTitle());
        holder.requestDescription.setText(listing.getDetails());
        Picasso.get()
                .load(listing.getPictureUrl())
                .placeholder(R.drawable.question_mark)
                .error(R.drawable.question_mark)
                .into(holder.productImage);
    }

    public void removeItem(int position) {
        FirebaseFirestore storage = FirebaseFirestore.getInstance();
        final DocumentReference docRef = storage.collection("requests").document(listings.get(position).getDocumentId());

        docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        Log.d("deleted:" , listings.get(position).getDocumentId());
        listings.remove(position);
        notifyItemRemoved(position);
        // Add whatever you want to do when removing an Item
    }

    @Override
    public int getItemCount() {
        return listings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView requestName;
        public TextView requestDescription;
        public ImageView productImage;
        public ImageButton deleteButton;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           requestName = itemView.findViewById(R.id.requestName);
           requestDescription = itemView.findViewById(R.id.requestDescription);
           productImage = itemView.findViewById(R.id.productImage);
           deleteButton = itemView.findViewById(R.id.deleteButton);

           deleteButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   removeItem(getAdapterPosition());
               }
           });
       }
   }
}
