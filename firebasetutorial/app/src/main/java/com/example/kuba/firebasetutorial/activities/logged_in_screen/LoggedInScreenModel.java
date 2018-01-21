package com.example.kuba.firebasetutorial.activities.logged_in_screen;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;

/**
 * Created by Konrad on 21.01.2018.
 */

public class LoggedInScreenModel {
    private Database db = FireDatabase.getInstance();
    LoggedInScreenView view;
    LoggedInScreenControler controler;

    public LoggedInScreenModel(LoggedInScreenView view, LoggedInScreenControler controler) {
        this.view = view;
        this.controler = controler;
    }

    void addNewList(String listName, String login) {
        db.addNewList(view, controler.userId, listName, controler.listCount, login);
    }

    void getAllLists() {
        db.getAllLists(view, controler, controler.login);
    }

}
