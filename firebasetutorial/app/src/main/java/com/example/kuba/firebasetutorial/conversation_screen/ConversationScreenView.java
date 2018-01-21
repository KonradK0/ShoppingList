package com.example.kuba.firebasetutorial.conversation_screen;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.R;

public class ConversationScreenView extends AppCompatActivity {
    LinearLayout conversationLinLayout;
    CardView answerCardView;
    ConversationScreenController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        conversationLinLayout = findViewById(R.id.messages_in_conv_lin_layout);
        answerCardView = findViewById(R.id.answer_card_view);
        controller = new ConversationScreenController(this);
        controller.setMessages();
        showMessages();
        setAnswerOnClick();
    }

    private void setAnswerOnClick(){
        answerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.startNewWriteMessageActivity();
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
        for(Message message : controller.messages){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20,20,20,20);
            CardView messageCardView = inflateMessageBox(message.getText(), messagesCount++);
            if(controller.login.equals(message.fromName)){
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
