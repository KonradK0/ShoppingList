package com.example.kuba.firebasetutorial;

import java.io.Serializable;

/**
 * Created by Kuba on 18/01/2018.
 */

public class Product implements Serializable {
    String productid;
    String name;


    public Product(String productid, String name) {
        this.productid = productid;
        this.name = name;

    }

    public Product() {
    }

    public String getProductid() {
        return productid;
    }

    public String getName() {
        return name;
    }

}
