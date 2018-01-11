package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by Konrad on 11.01.2018.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase database;

    public abstract UserDao userDao();
}
