package com.example.kuba.firebasetutorial.conversation_screen;

import com.example.kuba.firebasetutorial.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Konrad on 21.01.2018.
 */

public class ConversationScreenModel {

    void setMessages(ArrayList<Message> received, ArrayList<Message> sent, ArrayList<Message> messages){
        if(received != null) messages.addAll(received);
        if(sent != null) messages.addAll(sent);

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Long.valueOf(o1.getTimeStamp()).compareTo(Long.valueOf(o2.getTimeStamp()));
            }
        });
    }
}
