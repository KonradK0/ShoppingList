package com.example.kuba.firebasetutorial;

/**
 * Created by Konrad on 19.01.2018.
 */

public class Message {

    String title;
    String fromUid;
    String fromName;
    String toUid;
    String toName;
    String text;

    Message(){ }

    public Message(String title, String fromUid, String fromName, String toUid, String toName, String text) {
        this.title = title;
        this.fromUid = fromUid;
        this.fromName = fromName;
        this.toUid = toUid;
        this.toName = toName;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getFromUid() {
        return fromUid;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToUid() {
        return toUid;
    }

    public String getToName() {
        return toName;
    }

    public String getText() {
        return text;
    }
}
