package com.example.kuba.firebasetutorial.database;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.activities.main_activity.MainActivityController;
import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;
import com.example.kuba.firebasetutorial.activities.write_new_message_screen.WriteNewMessageScreenController;
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
