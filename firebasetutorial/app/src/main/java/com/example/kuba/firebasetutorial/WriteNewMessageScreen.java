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
    }

    public void sendMessageOnClick(View view) {
        db.getFirebaseDatabase().getReference().child("users").child(uid).child("login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    return;
//                }
                String recipientKey = dataSnapshot.getValue(String.class);
                Message message = new Message(titleEditText.getText().toString(), recipientKey, uid, messageEditText.getText().toString());
                DatabaseReference senderRef = db.getFirebaseDatabase().getReference().child("users").child(uid).child("messages").push();
                senderRef.setValue(message);
                DatabaseReference recipientRef = db.getFirebaseDatabase().getReference().child("users").child(recipientKey).child("messages").push();
                recipientRef.setValue(message);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
