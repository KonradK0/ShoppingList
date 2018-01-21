package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.example.kuba.firebasetutorial.messages_screen.MessagesScreenView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class WriteNewMessageScreen extends AppCompatActivity {
    EditText recipientEditText;
    EditText messageEditText;
    Button sendButton;
    Database db;
    String uid;
    String login;
    long messageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new_message_screen);
        recipientEditText = findViewById(R.id.message_to);
        recipientEditText.setText(getIntent().getStringExtra("RECIPENTNAME"));
        messageEditText = findViewById(R.id.new_message_edit_text);
        sendButton = findViewById(R.id.send_message_button);
        db = FireDatabase.getInstance();
        uid = getIntent().getStringExtra("USERID");
        messageCount = getIntent().getLongExtra("MESSAGECOUNT", -1);
        login = getIntent().getStringExtra("LOGIN");
    }

    public void sendMessageOnClick(View view) {
        db.getFirebaseDatabase().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if(postSnapshot.getValue(User.class).getLogin().equals(recipientEditText.getText().toString())){
                        String recipientUid = postSnapshot.getKey();
                        User recipient = postSnapshot.getValue(User.class);
                        Message message = new Message(uid, login, recipientUid, recipient.getLogin(), messageEditText.getText().toString(), String.valueOf(new Date().getTime()));
                        db.getFirebaseDatabase()
                                .getReference()
                                .child("users")
                                .child(recipientUid)
                                .child("messagesReceived")
                                .child(String.valueOf(recipient.getMessagesReceivedCount() + 1)).setValue(message);
                        db.getFirebaseDatabase()
                                .getReference("users")
                                .child(uid)
                                .child("messagesSent")
                                .child(String.valueOf(++messageCount))
                                .setValue(message);
                        Intent intent = new Intent(getApplicationContext(), MessagesScreenView.class);
                        intent.putExtra("USERID", uid);
                        intent.putExtra("LOGIN", login);
                        intent.putExtra("MESSAGECOUNT", messageCount);
                        startActivity(intent);
                        return;
                    }
                }
                Toast.makeText(getApplicationContext(), "Podany użytkownik nie istnieje", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
