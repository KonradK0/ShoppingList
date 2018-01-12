package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by Konrad on 12.01.2018.
 */

@Dao
public interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertProduct(Product product);

    @Query("SELECT * FROM product WHERE name = :name")
    public Product findByName(String name);

    @Query("SELECT * FROM Product WHERE (lower(name) LIKE '%' || :startsWith || '%')")
    public Product[] findProductsLike(String startsWith);
}
