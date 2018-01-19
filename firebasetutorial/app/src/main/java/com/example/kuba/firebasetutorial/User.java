package com.example.kuba.firebasetutorial;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kuba on 17/01/2018.
 */

public class User implements Serializable{
    String uid;
    String login;
    String password;

    public User() {
    }

    public User(String uid, String login, String password) {
        this.uid = uid;
        this.login = login;
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public static User empty(){
        return new User("", "", "");
    }

    public boolean isEmpty(){
        return "".equals(uid) && "".equals(login) && "".equals(password);
    }
}
