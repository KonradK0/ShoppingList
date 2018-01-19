package com.example.kuba.firebasetutorial;

/**
 * Created by Konrad on 19.01.2018.
 */

public class Message {

    String title;
    String from;
    String text;

    Message(){ }

    public Message(String title, String from, String text) {
        this.title = title;
        this.from = from;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }
}
