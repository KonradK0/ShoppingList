package com.example.kuba.firebasetutorial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 18/01/2018.
 */

public class ShoppingList implements Serializable {

    String listid;
    String name;
    List<Product> productList = new ArrayList<>();

    public ShoppingList() {
    }

    public ShoppingList(String listid, String name, List<Product> productList) {
        this.listid = listid;
        this.name = name;
        this.productList = productList;
    }

    public String getListid() {
        return listid;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProductList() {
        return productList;
    }
}
