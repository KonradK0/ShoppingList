package com.example.kuba.firebasetutorial.write_new_message_screen;

import android.content.Intent;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.example.kuba.firebasetutorial.messages_screen.MessagesScreenView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by Konrad on 21.01.2018.
 */

public class WriteNewMessageScreenModel {

    private static final Database db = FireDatabase.getInstance();
    WriteNewMessageScreenController controller;

    public WriteNewMessageScreenModel(WriteNewMessageScreenController controller) {
        this.controller = controller;
    }

    void sendMessage(final String recipentName, final String uid, final String login, final String messageText){
        db.sendMessage(controller, recipentName, uid, login, messageText);
    }
}
