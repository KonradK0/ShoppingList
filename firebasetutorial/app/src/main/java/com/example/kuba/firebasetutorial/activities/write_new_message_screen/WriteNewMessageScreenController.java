package com.example.kuba.firebasetutorial.activities.write_new_message_screen;

import android.content.Intent;

import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;

/**
 * Created by Konrad on 21.01.2018.
 */

public class WriteNewMessageScreenController {
    private WriteNewMessageScreenView view;
    private WriteNewMessageScreenModel model;
    private String uid;
    private String login;
    private long messageCount;

    WriteNewMessageScreenController(WriteNewMessageScreenView view) {
        this.view = view;
        this.model = new WriteNewMessageScreenModel(this);
        this.uid = view.getIntent().getStringExtra("USERID");
        this.messageCount = view.getIntent().getLongExtra("MESSAGECOUNT", -1);
        this.login = view.getIntent().getStringExtra("LOGIN");
    }

    void handleSendMessage(String recipentName, String messageText){
        model.sendMessage(recipentName, uid, login, messageText);
    }

    public void startMessagesScreenActivity(){
        Intent intent = new Intent(view, MessagesScreenView.class);
        intent.putExtra("USERID", uid);
        intent.putExtra("LOGIN", login);
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);
    }

    public WriteNewMessageScreenView getView() {
        return view;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(long messageCount) {
        this.messageCount = messageCount;
    }
}
