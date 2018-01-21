package com.example.kuba.firebasetutorial.messages_screen;

import com.example.kuba.firebasetutorial.Message;
import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Konrad on 21.01.2018.
 */

public class MessagesScreenModel {

    private final static Database db = FireDatabase.getInstance();
    private MessagesScreenController controller;

    public MessagesScreenModel(MessagesScreenController controller) {
        this.controller = controller;
    }

    public void getUsersMessages(String uid, final String login, String direction, final Map<String, ArrayList<Message>> userMessages) {
        db.getUsersMessages(controller.view, uid, direction, login, userMessages);
    }
}
