package com.example.kuba.firebasetutorial;

import java.io.Serializable;

/**
 * Created by Kuba on 18/01/2018.
 */

public class Product implements Serializable {
    String productid;
    String productname;


    public Product(String productname) {
        this.productname = productname;
    }

    public Product(String productid, String productname) {
        this.productid = productid;
        this.productname = productname;
    }

    public Product() {
    }

    public String getProductid() {
        return productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
