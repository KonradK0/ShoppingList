package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

/**
 * Created by Konrad on 14.01.2018.
 */
@Dao
public interface ProductJoinDetailsDao {
    @Query("SELECT * FROM product JOIN productjoindetails ON product.productUid = productjoindetails.productUid WHERE productjoindetails.detailsId = :listUid")
    public Product[] getProductsOnList(long listUid);
}
