package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Konrad on 11.01.2018.
 */

@Database(entities = {User.class, Product.class, Category.class, ShoppingList.class, ShoppingListDetails.class, ProductJoinDetails.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();
    public abstract ShoppingListDao shoppingListDao();
    public abstract ShoppingListDetailsDao shoppingListDetailsDao();

    public static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "instance-name").build();
        }
        return instance;
    }
}
