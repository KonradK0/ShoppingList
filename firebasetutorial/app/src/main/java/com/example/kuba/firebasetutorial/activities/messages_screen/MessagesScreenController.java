package com.example.kuba.firebasetutorial.activities.messages_screen;

import android.content.Intent;

import com.example.kuba.firebasetutorial.activities.conversation_screen.ConversationScreenView;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenView;
import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.activities.write_new_message_screen.WriteNewMessageScreenView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Konrad on 21.01.2018.
 */

class MessagesScreenController {
    MessagesScreenView view;
    private MessagesScreenModel model;
    private String login;
    private String uid;
    private long messageCount;
    final Map<String, ArrayList<Message>> messagesReceived = new HashMap<>();
    final Map<String, ArrayList<Message>> messagesSent = new HashMap<>();

    MessagesScreenController(MessagesScreenView presentationLayer) {
        this.view = presentationLayer;
        login = view.getIntent().getStringExtra("LOGIN");
        uid = view.getIntent().getStringExtra("USERID");
        messageCount = view.getIntent().getLongExtra("MESSAGECOUNT", -1);
        model = new MessagesScreenModel(this);
    }

    void handleGettingMessages() {
        model.getUsersMessages(uid, login, "messagesReceived", messagesReceived);
        model.getUsersMessages(uid, login, "messagesSent", messagesSent);
    }

    void startNewMessageActivity() {
        Intent intent = new Intent(view, WriteNewMessageScreenView.class);
        intent.putExtra("USERID", uid);
        intent.putExtra("LOGIN", login);
        intent.putExtra("RECIPENTNAME", "");
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);

    }

    void startHomeActivity(){
        Intent intent = new Intent(view, LoggedInScreenView.class);
        intent.putExtra("USERID", uid);
        intent.putExtra("LOGIN", login);
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);
    }

    void startNewConversationActivity(final String from){
        Intent intent = new Intent(view, ConversationScreenView.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", uid);
        intent.putExtra("MESSAGECOUNT", messageCount);
        intent.putExtra("MESSAGESSENT", messagesSent.get(from));
        intent.putExtra("MESSAGESRECEIVED", messagesReceived.get(from));
        intent.putExtra("SENTCOUNT", messagesSent.size());
        intent.putExtra("RECEIVEDCOUNT", messagesReceived.size());
        view.startActivity(intent);
    }
}
