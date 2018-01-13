package com.konrad.shoppinglist.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by Konrad on 13.01.2018.
 */

@Dao
public interface ShoppingListDetailsDao {

    @Insert
    public void insertProductToList(ShoppingListDetails details);

    @Delete
    public void deleteProductFromList(ShoppingListDetails details);

    @Query("SELECT * FROM product P JOIN shoppinglistdetails SD ON P.productUid = SD.productUid WHERE SD.listUid = :listUid")
    public Product[] getProductsOnList(long listUid);
}
