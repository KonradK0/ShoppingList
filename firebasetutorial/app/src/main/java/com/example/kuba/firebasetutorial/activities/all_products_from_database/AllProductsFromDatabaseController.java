package com.example.kuba.firebasetutorial.activities.all_products_from_database;

import android.content.Intent;

import com.example.kuba.firebasetutorial.activities.SearchProductsScreen;

/**
 * Created by Konrad on 21.01.2018.
 */

public class AllProductsFromDatabaseController {
    private AllProductsFromDatabaseView view;
    private AllProductsFromDatabaseModel model;

    private static String uid;
    private String login;
    private String listId;
    private String listName;

    public AllProductsFromDatabaseController(AllProductsFromDatabaseView view) {
        this.view = view;
        model = new AllProductsFromDatabaseModel(view, this);
        listId = view.getIntent().getStringExtra("LISTID");
        login = view.getIntent().getStringExtra("LOGIN");
        uid = view.getIntent().getStringExtra("USERID");
        listId = view.getIntent().getStringExtra("LISTID");
        listName = view.getIntent().getStringExtra("LISTNAME");
    }

    public void handleAllGetProducts() {
        model.getAllProducts();
    }

    public void handleAddProductToList(String productName) {
        model.addProductToList(uid, productName, listId);
    }

    void updateAndRestartActivity(){
        Intent intent = new Intent(view, SearchProductsScreen.class);
        intent.putExtra("LOGIN", login);
        intent.putExtra("USERID", uid);
        intent.putExtra("LISTID", listId);
        intent.putExtra("LISTNAME", listName);
        view.startActivity(intent);
    }
}
