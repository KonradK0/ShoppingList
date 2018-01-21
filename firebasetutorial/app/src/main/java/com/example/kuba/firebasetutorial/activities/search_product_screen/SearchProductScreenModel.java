package com.example.kuba.firebasetutorial.activities.search_product_screen;

import com.example.kuba.firebasetutorial.database.Database;
import com.example.kuba.firebasetutorial.database.FireDatabase;

/**
 * Created by Konrad on 21.01.2018.
 */

public class SearchProductScreenModel {
    private Database db = FireDatabase.getInstance();
    SearchProductScreenController controller;
    SearchProductsScreenView view;

    public SearchProductScreenModel(SearchProductScreenController controller, SearchProductsScreenView view) {
        this.controller = controller;
        this.view = view;
    }

    void removeProductFromList(String productName, String key){
        db.removeProductFromList(productName, key);
    }

    void getAllProductsFromList(){
        db.getProductsFromList(view, controller.listKey);
    }

    void addOwnProduct(String productName){
        db.addOwnProduct(controller, productName, controller.uid, controller.listKey);
    }

    void addNewOwner(String ownerName){
        db.addNewOwner(view, controller.listKey, ownerName);
    }
}
