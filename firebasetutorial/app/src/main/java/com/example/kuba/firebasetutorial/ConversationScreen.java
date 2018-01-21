package com.example.kuba.firebasetutorial;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ConversationScreen extends AppCompatActivity {
    ArrayList<Message> messages;
    LinearLayout conversationLinLayout;
    CardView answerCardView;
    String login;
    String uid;
    long messageCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        login = getIntent().getStringExtra("LOGIN");
        uid = getIntent().getStringExtra("USERID");
        messageCount = getIntent().getLongExtra("MESSAGECOUNT", -1);
        messages = new ArrayList<>();
        conversationLinLayout = findViewById(R.id.messages_in_conv_lin_layout);
        answerCardView = findViewById(R.id.answer_card_view);
        setMessages();
        showMessages();
        setAnswerOnClick();
    }

    private void setAnswerOnClick(){
        answerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WriteNewMessageScreen.class);
                intent.putExtra("USERID", uid);
                intent.putExtra("LOGIN", login);
                intent.putExtra("RECIPENTNAME", messages.get(0).getFromName().equals(login) ? messages.get(0).getToName() : messages.get(0).getFromName());
                intent.putExtra("MESSAGECOUNT", messageCount);
                startActivity(intent);
            }
        });
    }

    private void setMessages(){
        ArrayList<Message> received = (ArrayList<Message>) getIntent().getSerializableExtra("MESSAGESRECEIVED");
        ArrayList<Message> sent = (ArrayList<Message>) getIntent().getSerializableExtra("MESSAGESSENT");
        if(received != null) messages.addAll(received);
        if(sent != null) messages.addAll(sent);

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Long.valueOf(o1.getTimeStamp()).compareTo(Long.valueOf(o2.getTimeStamp()));
            }
        });
    }

    private CardView inflateMessageBox(String message, int childIndex) {
        LayoutInflater.from(getApplicationContext()).inflate(R.layout.message_box, conversationLinLayout, true);
        CardView singleListCardView = (CardView) conversationLinLayout.getChildAt(childIndex);
        TextView listNameTextView = (TextView) singleListCardView.getChildAt(0);
        listNameTextView.setText(message);
        return singleListCardView;
    }

    private void showMessages(){
        int messagesCount = 0;
        for(Message message : messages){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,20,20,20);
            CardView messageCardView = inflateMessageBox(message.getText(), messagesCount++);
            if(login.equals(message.fromName)){
                messageCardView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.lightGrey));
                params.gravity = Gravity.START;
            }
            else{
                params.gravity = Gravity.END;
            }
            messageCardView.setLayoutParams(params);
        }
    }
}
