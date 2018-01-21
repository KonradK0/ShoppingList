package com.example.kuba.firebasetutorial.activities.write_new_message_screen;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;

/**
 * Created by Konrad on 21.01.2018.
 */

public class WriteNewMessageScreenModel {

    private static final Database db = FireDatabase.getInstance();
    private WriteNewMessageScreenController controller;

    WriteNewMessageScreenModel(WriteNewMessageScreenController controller) {
        this.controller = controller;
    }

    void sendMessage(final String recipentName, final String uid, final String login, final String messageText){
        db.sendMessage(controller, recipentName, uid, login, messageText);
    }
}
