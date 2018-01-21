package com.example.kuba.firebasetutorial;

import java.io.Serializable;

/**
 * Created by Konrad on 19.01.2018.
 */

public class Message implements Serializable{

    String fromUid;
    public String fromName;
    String toUid;
    String toName;
    String text;
    String timeStamp;

    Message(){ }

    public Message(String fromUid, String fromName, String toUid, String toName, String text, String timeStamp) {
        this.fromUid = fromUid;
        this.fromName = fromName;
        this.toUid = toUid;
        this.toName = toName;
        this.text = text;
        this.timeStamp = timeStamp;
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

    public String getTimeStamp() {
        return timeStamp;
    }
}
