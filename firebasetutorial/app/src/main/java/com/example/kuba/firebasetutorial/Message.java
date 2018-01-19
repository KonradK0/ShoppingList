package com.example.kuba.firebasetutorial;

/**
 * Created by Konrad on 19.01.2018.
 */

public class Message {

    String title;
    String fromUid;
    String toUid;
    String text;

    Message(){ }

    public Message(String title, String fromUid, String toUid, String text) {
        this.title = title;
        this.fromUid = fromUid;
        this.toUid = toUid;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getFromUid() {
        return fromUid;
    }

    public String getToUid() {
        return toUid;
    }

    public String getText() {
        return text;
    }
}
