package com.konrad.shoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Konrad on 13.01.2018.
 */

@Entity(primaryKeys = {"listUid", "productUid"},
        foreignKeys = {@ForeignKey(entity = ShoppingList.class, parentColumns = "uid", childColumns = "listUid"),
                        @ForeignKey(entity = Product.class, parentColumns = "uid", childColumns = "productUid")})
public class ShoppingListDetails {
    @ColumnInfo(name = "listUid")
    private long listUid;
    @ColumnInfo(name = "productUid")
    private long productUid;

    public ShoppingListDetails(long listUid, long productUid) {
        this.listUid = listUid;
        this.productUid = productUid;
    }

    public long getListUid() {
        return listUid;
    }

    public long getProductUid() {
        return productUid;
    }
}