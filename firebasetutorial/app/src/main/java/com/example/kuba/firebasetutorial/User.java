package com.example.kuba.firebasetutorial;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kuba on 17/01/2018.
 */

public class User implements Serializable {
    String login;
    String password;
    List<ShoppingList> shoppingLists;
    List<Message> messagesReceived;
    List<Message> messagesSent;

    public User() {
    }

    public User(String login, String password, List<ShoppingList> shoppingLists, List<Message> messagesReceived, List<Message> messagesSent) {
        this.login = login;
        this.password = password;
        this.shoppingLists = shoppingLists;
        this.messagesReceived = messagesReceived;
        this.messagesSent = messagesSent;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public List<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    public long getMessagesReceivedCount() {
        return messagesReceived == null ? -1 : messagesReceived.size();
    }

    public long getMessagesSentCount() {
        return messagesSent == null ? -1 : messagesSent.size();
    }

}
