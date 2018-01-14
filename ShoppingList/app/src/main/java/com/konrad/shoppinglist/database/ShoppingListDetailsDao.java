package com.konrad.shoppinglist.database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

/**
 * Created by Konrad on 13.01.2018.
 */

@Dao
public interface ShoppingListDetailsDao {

    @Insert
    public void insertProductToList(ShoppingListDetails details);

    @Delete
    public void deleteProductFromList(ShoppingListDetails details);

}
