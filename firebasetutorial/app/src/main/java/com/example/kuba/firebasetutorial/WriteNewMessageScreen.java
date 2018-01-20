package com.example.kuba.firebasetutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class WriteNewMessageScreen extends AppCompatActivity {
    EditText recipientEditText;
    EditText titleEditText;
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
        titleEditText = findViewById(R.id.title_edit_text);
        messageEditText = findViewById(R.id.new_message_edit_text);
        sendButton = findViewById(R.id.send_message_button);
        db = FireDatabase.getInstance();
        uid = getIntent().getStringExtra("USERID");
        login = getIntent().getStringExtra("LOGIN");
        messageCount = getIntent().getLongExtra("MESSAGECOUNT", -1);
    }

    public void sendMessageOnClick(View view) {
        db.getFirebaseDatabase().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    if(postSnapshot.getValue(User.class).getLogin().equals(recipientEditText.getText().toString())){
                        String recipientUid = postSnapshot.getKey();
                        User recipient = postSnapshot.getValue(User.class);
                        Message message = new Message(titleEditText.getText().toString(), uid, login, recipientUid, recipient.getLogin(), messageEditText.getText().toString());
                        db.getFirebaseDatabase()
                                .getReference()
                                .child("users")
                                .child(recipientUid)
                                .child("messages")
                                .child(String.valueOf(recipient.getMessagesCount() + 1)).setValue(message);
                        db.getFirebaseDatabase()
                                .getReference("users")
                                .child(uid)
                                .child("messages")
                                .child(String.valueOf(messageCount + 1))
                                .setValue(message);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
