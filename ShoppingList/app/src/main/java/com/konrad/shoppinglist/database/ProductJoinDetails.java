package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

/**
 * Created by Konrad on 14.01.2018.
 */


@Entity(primaryKeys = {"productUid", "shoppingListDetailsUid"},
        foreignKeys = {@ForeignKey(entity = Product.class, parentColumns = "uid", childColumns = "productId"),
                        @ForeignKey(entity = ShoppingListDetails.class, parentColumns = "listUid", childColumns = "detailsId")})
public class ProductJoinDetails {

    private long productIid;
    private long detailsId;

    public ProductJoinDetails(long productIid, long detailsId) {
        this.productIid = productIid;
        this.detailsId = detailsId;
    }

    public long getProductIid() {
        return productIid;
    }

    public long getDetailsId() {
        return detailsId;
    }
}
