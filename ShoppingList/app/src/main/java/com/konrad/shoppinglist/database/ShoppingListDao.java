package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by Konrad on 13.01.2018.
 */

@Dao
public interface ShoppingListDao {

    @Insert
    public void insertShoppingList(ShoppingList shoppingList);

    @Delete
    public void deleteShoppingList(ShoppingList shoppingList);

    @Query("SELECT * FROM shoppinglist WHERE userUid = :userId")
    public ShoppingList[] getUsersLists(long userId);
}
