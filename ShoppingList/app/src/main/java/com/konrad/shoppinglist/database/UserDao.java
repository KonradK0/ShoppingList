package com.konrad.shoppinglist.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.math.BigInteger;

/**
 * Created by Konrad on 11.01.2018.
 */

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE login = :login")
    User findByLogin(String login);

    @Query("SELECT * FROM user")
    User[] getAllUsers();
}
