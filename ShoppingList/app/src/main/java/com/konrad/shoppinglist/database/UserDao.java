package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by Konrad on 11.01.2018.
 */

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE login = :login" )
    User findByLogin(String login);

    @Query("SELECT * FROM user")
    User[] getAllUsers();
}
