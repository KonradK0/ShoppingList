package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesScreen extends AppCompatActivity {
    Database db;
    LinearLayout messagesLinLayout;
    String uid;
    final Map<String, ArrayList<String>> messagesFromUsers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_screen);
        messagesLinLayout = findViewById(R.id.users_messages);
        db = FireDatabase.getInstance();
        uid = getIntent().getStringExtra("USERID");
        getUsersMessages();
    }

    private CardView inflateMessageBox(String fromUser, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.shopping_list_box, messagesLinLayout, true);
        CardView singleListCardView = (CardView) messagesLinLayout.getChildAt(childIndex);
        TextView listNameTextView = (TextView) singleListCardView.getChildAt(0);
        listNameTextView.setText(fromUser);
        return singleListCardView;
    }

    void setOnMessageBoxOnClick(CardView cardView, final String from){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SingleMessage.class);
                intent.putExtra("FROM", from);
                intent.putExtra("MESSAGES", messagesFromUsers.get(from));
                startActivity(intent);
            }
        });
    }

    void setupMessageBox(String from, int index){
        CardView messageCardView = inflateMessageBox(from, index);
        setOnMessageBoxOnClick(messageCardView, from);
    }

    public void getUsersMessages(){
        DatabaseReference messages = db.getFirebaseDatabase().getReference().child("users").child(uid).child("messagesLinLayout");
        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Message message = postSnapshot.getValue(Message.class);
                    String from = message.getFrom();
                    String text = message.getText();
                    if(messagesFromUsers.get(from) == null){
                        messagesFromUsers.put(from, new ArrayList<String>());
                    }
                    messagesFromUsers.get(from).add(text);
                }

                int currentUser = 0;
                for(String user : messagesFromUsers.keySet()){
                    setupMessageBox(user, currentUser++);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
