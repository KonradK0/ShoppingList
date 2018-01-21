package com.example.kuba.firebasetutorial;

import com.example.kuba.firebasetutorial.activities.Owner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuba on 18/01/2018.
 */

public class ShoppingList implements Serializable {

    String name;
    List<Product> productList = new ArrayList<>();
    List<Owner> owners = new ArrayList<>();
    String numberOfAddedProducts;

    public ShoppingList() {
        numberOfAddedProducts = String.valueOf(0);
    }


    public ShoppingList(String name, List<Product> productList, List<Owner> ownersList) {
        this.name = name;
        this.productList = productList;
        this.owners = ownersList;
        numberOfAddedProducts = String.valueOf(0);
    }


    public String getName() {
        return name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Owner> getOwners() {
        return owners;
    }

}
