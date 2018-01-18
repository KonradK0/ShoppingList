package com.example.kuba.firebasetutorial;

import java.util.List;

/**
 * Created by Kuba on 18/01/2018.
 */

public class ShoppingList {

    String listid;
    String ownerid;
    List<Product> productList;

    public ShoppingList() {
    }

    public ShoppingList(String listid, String ownerid, List<Product> productList) {
        this.listid = listid;
        this.ownerid = ownerid;
        this.productList = productList;
    }

    public String getListid() {
        return listid;
    }
}
