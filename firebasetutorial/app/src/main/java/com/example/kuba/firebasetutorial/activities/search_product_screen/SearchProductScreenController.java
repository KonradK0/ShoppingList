package com.example.kuba.firebasetutorial.activities.search_product_screen;

import android.content.Intent;

import com.example.kuba.firebasetutorial.activities.all_products_from_database.AllProductsFromDatabaseView;
import com.example.kuba.firebasetutorial.activities.logged_in_screen.LoggedInScreenView;

/**
 * Created by Konrad on 21.01.2018.
 */

public class SearchProductScreenController {
    SearchProductsScreenView view;
    SearchProductScreenModel model;
    String uid;
    String listKey;

    public SearchProductScreenController(SearchProductsScreenView view) {
        this.view = view;
        model = new SearchProductScreenModel(this, view);
        listKey = view.getIntent().getStringExtra("LISTID");
        uid = view.getIntent().getStringExtra("USERID");
    }

    void handleRemoveProductFromList(String productName) {
        model.removeProductFromList(productName, listKey);
    }

    void handleGetAllProductsFromList() {
        model.getAllProductsFromList();
    }

    void handleAddOwnProduct(String productName){
        model.addOwnProduct(productName);
    }

    void handleAddNewOwner(String ownerName){
        model.addNewOwner(ownerName);
    }

    void putExtras(Intent intent){
        intent.putExtra("LOGIN", view.getIntent().getStringExtra("LOGIN"));
        intent.putExtra("USERID", view.getIntent().getStringExtra("USERID"));
        intent.putExtra("LISTID", view.getIntent().getStringExtra("LISTID"));
        intent.putExtra("LISTNAME", view.getIntent().getStringExtra("LISTNAME"));
        intent.putExtra("MESSAGECOUNT", view.getIntent().getStringExtra("MESSAGECOUNT"));
    }
    void startAllProductsFromDatabaseActivity(){
        Intent intent = new Intent(view, AllProductsFromDatabaseView.class);
        putExtras(intent);
        view.startActivity(intent);
    }

    public void restartActivity(){
        Intent intent = new Intent(view, SearchProductsScreenView.class);
        putExtras(intent);
        view.startActivity(intent);
    }

    void startLoggedInActivity(){
        Intent intent = new Intent(view, LoggedInScreenView.class);
        putExtras(intent);
        view.startActivity(intent);
    }
}
