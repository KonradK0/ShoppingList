package com.example.kuba.firebasetutorial.database;

import android.content.Context;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.main_activity.MainActivityController;
import com.example.kuba.firebasetutorial.messages_screen.MessagesScreenView;
import com.example.kuba.firebasetutorial.write_new_message_screen.WriteNewMessageScreenController;
import com.example.kuba.firebasetutorial.write_new_message_screen.WriteNewMessageScreenView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Konrad on 19.01.2018.
 */

public interface Database {

    int getListsCount();
    void getUsersMessages(final MessagesScreenView view, String uid, String direction, final String login, final Map<String, ArrayList<Message>> userMessages);
    void sendMessage(final WriteNewMessageScreenController controller, final String recipentName, final String uid, final String login, final String text);
    void checkCredentials(final MainActivityController controller, final String login, final String password);
    FirebaseDatabase getFirebaseDatabase();
}
