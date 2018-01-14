package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Konrad on 14.01.2018.
 */


@Entity(primaryKeys = {"productId", "listId"},
        foreignKeys = @ForeignKey(entity = Product.class, parentColumns = "uid", childColumns = "productId"))
public class ProductJoinDetails {

    private long productId;
    private long listId;

    public ProductJoinDetails(long productId, long listId) {
        this.productId = productId;
        this.listId = listId;
    }

    public long getProductId() {
        return productId;
    }

    public long getListId() {
        return listId;
    }
}
