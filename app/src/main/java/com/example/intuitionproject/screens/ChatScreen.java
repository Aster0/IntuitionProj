package com.example.intuitionproject.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.intuitionproject.R;
import com.example.intuitionproject.adapters.MessageAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatScreen extends AppCompatActivity {
    /**
     * testestest
     */
    private String document, firstMessage, requestID;
    private Map<String, String> chatLog;
    private final boolean chatReady = false;
    private boolean requestOwn;
    private TextView chatMessage;
    private int count = 0;

    private static List<String> receiverName = new ArrayList<>();

    public static List<String> getReceiverName()
    {
        return receiverName;
    }
    private  RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private Button acceptButton;
    private ImageButton exitButton;
    private List<String> replies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        chatMessage = findViewById(R.id.chat_field);
        acceptButton = findViewById(R.id.acceptBtn);
        exitButton = findViewById(R.id.exit_button);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
            public void onSuccess(final DocumentSnapshot documentSnapshot) {


                replies = (List<String>) documentSnapshot.get("replies");



                final String targetName = documentSnapshot.get("username").toString();

                FirebaseFirestore.getInstance().collection("requests").document(documentSnapshot.get("target").toString())
                        .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot1) {


                        if(targetName.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                || FirebaseAuth.getInstance().getCurrentUser().getEmail().equals(documentSnapshot1.get("userid")))
                        {

                            TextView username = findViewById(R.id.username);

                            receiverName.add(documentSnapshot1.get("userid").toString()); // index 0
                            receiverName.add(targetName); // index 1

                            Map<String, String> details = new HashMap<>();

                            List<String> chatMessages = (List<String>) documentSnapshot.get("replies");


                            String userType;

                            if(documentSnapshot1.get("userid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                            {

                                // user that requested



                                userType = "requester_last_text";


                                username.setText(receiverName.get(1));
                                requestOwn = true;
                            }
                            else
                            {
                                //receiverName = documentSnapshot1.get("userid").toString();

                                userType = "sender_last_text";
                                username.setText(receiverName.get(0));
                                requestOwn = false;
                            }


                            if(chatMessages != null)
                                details.put(userType, chatMessages.get(chatMessages.size() - 1)); // last entry
                            System.out.println(details);
                            System.out.println("DETAILS!");

                            FirebaseFirestore.getInstance().collection("chats").document(document).set(details, SetOptions.merge());


                            System.out.println(documentSnapshot1.get("userid").toString() + " USERID");
                            System.out.println(FirebaseAuth.getInstance().getCurrentUser().getEmail() + " CURRENT USER");




                            TextView productName = findViewById(R.id.productName);
                            productName.setText(documentSnapshot1.get("title").toString());

                            ImageView productImage = findViewById(R.id.productImage);

                            Picasso.get().load(documentSnapshot1.get("picture-url").toString()).into(productImage);

                            TextView price = findViewById(R.id.price);
                            price.setText("S$" + documentSnapshot1.get("payment").toString());

                            if(documentSnapshot1.get("accepted_by") != null || !(documentSnapshot1.get("userid").toString().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())))
                            {
                                acceptButton.setVisibility(View.GONE);
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

        accepted.put("accepted_by", receiverName.get(1));
        FirebaseFirestore.getInstance().collection("requests").document(requestID).set(accepted, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                acceptButton.setVisibility(View.GONE);
                sendMessage("The requester has accepted you!");
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
        // don't send empty message
        if(!chatMessage.getText().toString().isEmpty()) {
            FirebaseFirestore.getInstance().collection("chats").document(document).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {


                    String modifier = "r}"; // not owner of request.


                    if (requestOwn == true) {
                        modifier = "s}"; // it's the owner of the request
                    }


                    Map<String, List<String>> test = new HashMap<>();

                    replies.add(modifier + chatMessage.getText().toString());
                    test.put("replies", replies);

                    Map<String, String> details = new HashMap<>();


                    if(requestOwn)
                        details.put("requester_last_text", modifier + chatMessage.getText().toString());
                    else
                        details.put("sender_last_text", modifier + chatMessage.getText().toString());

                    FirebaseFirestore.getInstance().collection("chats").document(document).set(details, SetOptions.merge());
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

    public void sendMessage(final String message)
    {
        FirebaseFirestore.getInstance().collection("chats").document(document).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {



               String modifier = "s}"; // it's the owner of the request



                Map<String, List<String>> test = new HashMap<>();
                Map<String, String> details = new HashMap<>();

                replies.add(modifier + message);
                test.put("replies", replies);


                details.put("requester_last_text", message);

                FirebaseFirestore.getInstance().collection("chats").document(document).set(details, SetOptions.merge());
                FirebaseFirestore.getInstance().collection("chats").document(document).set(test, SetOptions.merge());

            }
        });






    }
}