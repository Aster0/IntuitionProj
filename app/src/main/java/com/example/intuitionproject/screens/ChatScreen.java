package com.example.intuitionproject.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intuitionproject.R;
import com.example.intuitionproject.adapters.ChatBrowseAdapter;
import com.example.intuitionproject.adapters.MessageAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatScreen extends AppCompatActivity {
    /**
     * testestest
     */
    private String document, firstMessage, requestID;
    private Map<String, String> chatLog;
    private boolean chatReady = false, requestOwn;
    private TextView chatMessage;
    private int count = 0;

    private static String receiverName;

    public static String getReceiverName()
    {
        return receiverName;
    }
    private  RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private Button acceptButton;
    private List<String> replies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        chatMessage = findViewById(R.id.chat_field);
        acceptButton = findViewById(R.id.acceptBtn);

        retrieveInfo();

        MessageAdapter.setMessages(new ArrayList<String>());

        buildChat();
    }

    public void retrieveInfo()
    {
       document = getIntent().getStringExtra("id");
    }

    private void buildChat()
    {
        FirebaseFirestore.getInstance().collection("chats").document(document).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                replies = (List<String>) documentSnapshot.get("replies");



                final String targetName = documentSnapshot.get("username").toString();

                FirebaseFirestore.getInstance().collection("requests").document(documentSnapshot.get("target").toString())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot1) {


                        if(targetName.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                || FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(documentSnapshot1.get("userid")))
                        {
                            if(!documentSnapshot1.get("userid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                            {
                                receiverName = targetName;
                                requestOwn = true;
                            }
                            else
                            {
                                receiverName = documentSnapshot1.get("userid").toString();
                                requestOwn = false;
                            }

                            System.out.println(documentSnapshot1.get("userid").toString() + " USERID");
                            System.out.println(FirebaseAuth.getInstance().getCurrentUser().getEmail() + " CURRENT USER");


                            TextView username = findViewById(R.id.username);
                            username.setText(receiverName);

                            TextView productName = findViewById(R.id.productName);
                            productName.setText(documentSnapshot1.get("title").toString());

                            ImageView productImage = findViewById(R.id.productImage);

                            Picasso.get().load(documentSnapshot1.get("picture-url").toString()).into(productImage);

                            TextView price = findViewById(R.id.price);
                            price.setText("S$" + documentSnapshot1.get("payment").toString());

                            if(documentSnapshot1.get("accepted_by") != null || !(documentSnapshot1.get("userid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())))
                            {
                                acceptButton.setVisibility(View.INVISIBLE);
                            }

                            requestID = documentSnapshot1.getId();


                            prepareListeners();
                        }

                    }
                });
            }
        });
    }

    public void acceptRequest(View view)
    {
        Map<String, String> accepted = new HashMap<>();

        accepted.put("accepted_by", receiverName);
        FirebaseFirestore.getInstance().collection("requests").document(requestID).set(accepted, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                acceptButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void prepareListeners() // to make the chat animation smoother
    {

        FirebaseFirestore.getInstance().collection("chats").document(document).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {


                MessageAdapter.getMessages().clear();

                addMessage(value);

                if(count == 0) {
                    recyclerView = findViewById(R.id.chatRecycledView);


                    messageAdapter = new MessageAdapter();
                    recyclerView.setAdapter(messageAdapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(ChatScreen.this));
                    count++;
                }

                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());





            }
        });
    }



    private void addMessage(DocumentSnapshot documentSnapshot)
    {

        if((List<String>) documentSnapshot.get("replies") != null) {
            for (String message : (List<String>) documentSnapshot.get("replies")) {

                MessageAdapter.getMessages().add(message);
                System.out.println(message);
            }
        }
        else
        {
            replies = new ArrayList<>();
        }
    }




    public void sendMessage(View view)
    {


        FirebaseFirestore.getInstance().collection("chats").document(document).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                String modifier = "r}"; // not owner of request.



                if(requestOwn == true)
                {
                    modifier = "s}"; // it's the owner of the request
                }


                Map<String, List<String>> test = new HashMap<>();

                replies.add(modifier + chatMessage.getText().toString());
                test.put("replies", replies);

                FirebaseFirestore.getInstance().collection("chats").document(document).set(test, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        chatMessage.setText("");
                    }
                });

            }
        });






    }
}