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

public class MessagesScreen extends AppCompatActivity {
    Database db;
    LinearLayout messages;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_screen);
        messages = findViewById(R.id.users_messages);
        db = FireDatabase.getInstance();
        uid = getIntent().getStringExtra("USERID");
    }

    private CardView inflateMessageBox(String fromUser, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.shopping_list_box, messages, true);
        CardView singleListCardView = (CardView) messages.getChildAt(childIndex);
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
                startActivity(intent);
            }
        });
    }

    public void getUsersMessages(){
        DatabaseReference messages = db.getFirebaseDatabase().getReference().child("users").child(uid).child("messages");
        messages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    postSnapshot.child("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })

        //TODO pobrać z bazy wiadomości i wykorzystać dwie powyższe funkcje
    }
}
