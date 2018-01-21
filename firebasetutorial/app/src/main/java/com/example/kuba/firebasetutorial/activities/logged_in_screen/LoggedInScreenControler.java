package com.example.kuba.firebasetutorial.activities.logged_in_screen;

import android.content.Intent;
import android.util.Log;

import com.example.kuba.firebasetutorial.activities.search_product_screen.SearchProductsScreenView;
import com.example.kuba.firebasetutorial.activities.messages_screen.MessagesScreenView;

/**
 * Created by Konrad on 21.01.2018.
 */

public class LoggedInScreenControler {
    LoggedInScreenView view;
    LoggedInScreenModel model;
    String userId;
    String login;
    long messageCount;
    int listCount = 0;

    public LoggedInScreenControler(LoggedInScreenView view) {
        this.view = view;
        model = new LoggedInScreenModel(view, this);

        userId = view.getIntent().getStringExtra("USERID");
        login = view.getIntent().getStringExtra("LOGIN");
        messageCount = view.getIntent().getLongExtra("MESSAGECOUNT", -1);
    }

    void handleAddNewList(String listName) {
        model.addNewList(listName, login);
    }

    void handleGetAllLists() {
        model.getAllLists();
        Log.e("LISTCOUNT", String.valueOf(listCount));
    }

    public void setListCount(int listCount) {
        this.listCount = listCount;
    }

    void startSearchProductsActivity(String listName, String listId) {
        Intent intent = new Intent(view, SearchProductsScreenView.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", userId);
        intent.putExtra("MESSAGECOUNT", messageCount);
        intent.putExtra("LISTNAME", listName);
        intent.putExtra("LISTID", listId);
        view.startActivity(intent);
    }

    void startNewMessagesActivity() {
        Intent intent = new Intent(view, MessagesScreenView.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", userId);
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);
    }

    void restartActivity() {
        Intent intent = new Intent(view, LoggedInScreenView.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", userId);
        intent.putExtra("MESSAGECOUNT", messageCount);
        view.startActivity(intent);
    }
}
