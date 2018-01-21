package com.example.kuba.firebasetutorial.messages_screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.LoggedInScreen;
import com.example.kuba.firebasetutorial.R;

import java.util.HashSet;
import java.util.Set;

public class MessagesScreenView extends AppCompatActivity {
    LinearLayout messagesLinLayout;
    CardView newMessageCardView;
    String uid;
    String login;
    long messageCount;
    MessagesScreenController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_screen);
        login = getIntent().getStringExtra("LOGIN");
        uid = getIntent().getStringExtra("USERID");
        messageCount = getIntent().getLongExtra("MESSAGECOUNT", -1);
        messagesLinLayout = findViewById(R.id.users_messages);
        newMessageCardView = findViewById(R.id.new_message_card_view);
        controller = new MessagesScreenController(this);
        controller.handleGettingMessages();
        setNewMessageCardViewOnClickListener();
    }

    public void onClickHome(View view){
        controller.startHomeActivity();
    }

    void setNewMessageCardViewOnClickListener() {
        newMessageCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startNewMessageActivity();
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
                controller.startNewConversationActivity(from);
            }
        });
    }

    public void setupMessageBox() {
        messagesLinLayout.removeAllViews();
        int currentUser = 0;
        Set<String> allConversations = new HashSet<>();
        allConversations.addAll(controller.messagesSent.keySet());
        allConversations.addAll(controller.messagesReceived.keySet());
        for (String user : allConversations) {
            CardView messageCardView = inflateMessageBox(user, currentUser++);
            setOnMessageBoxOnClick(messageCardView, user);
        }
    }

}
