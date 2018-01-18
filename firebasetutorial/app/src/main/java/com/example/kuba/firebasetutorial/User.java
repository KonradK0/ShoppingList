package com.example.kuba.firebasetutorial;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kuba on 17/01/2018.
 */

public class User {
    String uid;
    String login;
    String password;
    ArrayList<String> arrayList = new ArrayList<>();
    public User() {
    }

    public User(String uid, String login, String password) {
        this.uid = uid;
        this.login = login;
        this.password = password;
    }
}
