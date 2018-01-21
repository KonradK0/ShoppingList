package com.example.kuba.firebasetutorial.database;

import android.content.Context;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.messages_screen.MessagesScreenView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Konrad on 19.01.2018.
 */

public interface Database {

    int getUserCount();
    int getListsCount();
    int incrementUserCounter();
    void getUsersMessages(final MessagesScreenView view, String uid, String direction, final String login, final Map<String, ArrayList<Message>> userMessages);
    FirebaseDatabase getFirebaseDatabase();
}
