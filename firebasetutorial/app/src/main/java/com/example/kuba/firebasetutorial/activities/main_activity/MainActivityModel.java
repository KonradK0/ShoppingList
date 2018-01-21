package com.example.kuba.firebasetutorial.activities.main_activity;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;

/**
 * Created by Konrad on 21.01.2018.
 */

class MainActivityModel {

    private static Database db = FireDatabase.getInstance();
    private MainActivityController controller;

    MainActivityModel(MainActivityController controller) {
        this.controller = controller;
    }

    void checkCredentials(final String login, final String password) {
        db.checkCredentials(controller, login, password);
    }

}
