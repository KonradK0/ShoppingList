package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by Konrad on 12.01.2018.
 */

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertCategory(Category category);

    @Query("SELECT uid FROM category WHERE name = :name")
    public long getCategoryUidFromName(String name);
}
