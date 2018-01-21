package com.example.kuba.firebasetutorial.messages_screen;

import android.content.Intent;

import com.example.kuba.firebasetutorial.ConversationScreen;
import com.example.kuba.firebasetutorial.LoggedInScreen;
import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.WriteNewMessageScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Konrad on 21.01.2018.
 */

public class MessagesScreenController {
    MessagesScreenView view;
    private MessagesScreenModel model;
    String login;
    String uid;
    long messageCount;
    final Map<String, ArrayList<Message>> messagesReceived = new HashMap<>();
    final Map<String, ArrayList<Message>> messagesSent = new HashMap<>();

    MessagesScreenController(MessagesScreenView presentationLayer) {
        this.view = presentationLayer;
        this.login = view.login;
        this.uid = view.uid;
        this.messageCount = view.messageCount;
        model = new MessagesScreenModel(this);
    }

    void handleGettingMessages() {
        model.getUsersMessages(uid, login, "messagesReceived", messagesReceived);
        model.getUsersMessages(uid, login, "messagesSent", messagesSent);
    }

    void startNewMessageActivity() {
        Intent intent = new Intent(view, WriteNewMessageScreen.class);
        intent.putExtra("USERID", uid);
        intent.putExtra("LOGIN", login);
        intent.putExtra("RECIPENTNAME", "");
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);

    }

    void startHomeActivity(){
        Intent intent = new Intent(view, LoggedInScreen.class);
        intent.putExtra("USERID", uid);
        intent.putExtra("LOGIN", login);
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);
    }

    void startNewConversationActivity(final String from){
        Intent intent = new Intent(view, ConversationScreen.class);
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
