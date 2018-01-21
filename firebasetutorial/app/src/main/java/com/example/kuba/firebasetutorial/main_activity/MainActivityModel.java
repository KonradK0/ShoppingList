package com.example.kuba.firebasetutorial.main_activity;

import android.util.Log;
import android.widget.Toast;

import com.example.kuba.firebasetutorial.User;
import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Konrad on 21.01.2018.
 */

public class MainActivityModel {

    private static Database db = FireDatabase.getInstance();
    MainActivityController controller;

    public MainActivityModel(MainActivityController controller) {
        this.controller = controller;
    }

    void checkCredentials(final String login, final String password) {
        db.checkCredentials(controller, login, password);
    }

}
