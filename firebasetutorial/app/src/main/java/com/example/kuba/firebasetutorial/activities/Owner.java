package com.example.kuba.firebasetutorial.activities;

/**
 * Created by Kuba on 21/01/2018.
 */

public class Owner {

    String login;

    public Owner(String login) {
        this.login = login;
    }

    public Owner() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {

        return login;
    }
}
