package com.example.kuba.firebasetutorial.activities.all_products_from_database;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;

/**
 * Created by Konrad on 21.01.2018.
 */

public class AllProductsFromDatabaseModel {
    private Database db = FireDatabase.getInstance();
    private AllProductsFromDatabaseView view;

    public AllProductsFromDatabaseModel(AllProductsFromDatabaseView view, AllProductsFromDatabaseController controller) {
        this.view = view;
    }

    void getAllProducts(){
        db.getAllProducts(view);
    }

    void addProductToList(String uid, String productName, String listId){
        ///TODO zmień pierwsze listId na listName
        db.addProductToList(uid, productName, listId);
    }
}
