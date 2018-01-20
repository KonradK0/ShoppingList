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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MessagesScreen extends AppCompatActivity {
    static Database db = FireDatabase.getInstance();;
    LinearLayout messagesLinLayout;
    CardView newMessageCardView;
    String uid;
    String login;
    long messageCount;
    final Map<String, ArrayList<Message>> messagesReceived = new HashMap<>();
    final Map<String, ArrayList<Message>> messagesSent = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_screen);
        login = getIntent().getStringExtra("LOGIN");
        uid = getIntent().getStringExtra("USERID");
        messageCount = getIntent().getLongExtra("MESSAGECOUNT", -1);
        messagesLinLayout = findViewById(R.id.users_messages);
        newMessageCardView = findViewById(R.id.new_message_card_view);
        getUsersMessages("messagesReceived", messagesReceived);
        getUsersMessages("messagesSent", messagesSent);
        setNewMessageCardViewOnClickListener();
    }

    void setNewMessageCardViewOnClickListener() {
        newMessageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteNewMessageScreen.class);
                intent.putExtra("USERID", uid);
                intent.putExtra("LOGIN", login);
                intent.putExtra("RECIPENTNAME", "");
                intent.putExtra("MESSAGECOUNT", messageCount);
                startActivity(intent);
            }
        });
    }

    private CardView inflateMessageBox(String fromUser, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.shopping_list_box, messagesLinLayout, true);
        CardView singleListCardView = (CardView) messagesLinLayout.getChildAt(childIndex);
        TextView listNameTextView = (TextView) singleListCardView.getChildAt(0);
        listNameTextView.setText(fromUser);
        return singleListCardView;
    }

    void setOnMessageBoxOnClick(CardView cardView, final String from) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Conversation.class);
                intent.putExtra("LOGIN", login);
                intent.putExtra("USERID", uid);
                intent.putExtra("MESSAGECOUNT", messageCount);
                intent.putExtra("MESSAGESSENT", messagesSent.get(from));
                intent.putExtra("MESSAGESRECEIVED", messagesReceived.get(from));
                intent.putExtra("SENTCOUNT", messagesSent.size());
                intent.putExtra("RECEIVEDCOUNT", messagesReceived.size());
                startActivity(intent);
            }
        });
    }

    public void setupMessageBox() {
        messagesLinLayout.removeAllViews();
        int currentUser = 0;
        Set<String> allConversations = new HashSet<>();
        allConversations.addAll(messagesSent.keySet());
        allConversations.addAll(messagesReceived.keySet());
        for (String user : allConversations) {
            CardView messageCardView = inflateMessageBox(user, currentUser++);
            setOnMessageBoxOnClick(messageCardView, user);
        }
    }

    public void getUsersMessages(String direction, final Map<String, ArrayList<Message>> userMessages) {
        final DatabaseReference messages = db.getFirebaseDatabase().getReference().child("users")
                .child(uid)
                .child(direction);
        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    String from = login.equals(message.getFromName())? message.getToName() : message.getFromName(); //TODO message.getFromName() jak zmienimy loginy na unikalne
                    if (userMessages.get(from) == null) {
                        userMessages.put(from, new ArrayList<Message>());
                    }
                    userMessages.get(from).add(message);
                }
                    setupMessageBox();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
