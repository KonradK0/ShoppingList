package com.example.kuba.firebasetutorial.activities.main_activity;

import android.content.Intent;

import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenView;
import com.example.kuba.firebasetutorial.activities.register_screen.RegisterScreenView;
import com.example.kuba.firebasetutorial.User;

/**
 * Created by Konrad on 21.01.2018.
 */

public class MainActivityController {

    private MainActivityView view;
    private MainActivityModel model;
    private User found;

    MainActivityController(MainActivityView view) {
        this.view = view;
        model = new MainActivityModel(this);
    }

    void handleCheckCredentials(String login, String password) {
        model.checkCredentials(login, password);
    }

    void startRegisterView() {
        view.startActivity(new Intent(view, RegisterScreenView.class));
    }

    public void startLoggedInView(String uid, String login) {
        Intent intent = new Intent(view, LoggedInScreenView.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", uid);
        intent.putExtra("MESSAGECOUNT", found.getMessagesSentCount());
        view.startActivity(intent);
    }

    public MainActivityView getView() {
        return view;
    }

    public User getFound() {
        return found;
    }

    public void setFound(User found) {
        this.found = found;
    }
}
