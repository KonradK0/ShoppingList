package com.konrad.shoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Konrad on 11.01.2018.
 */

@Entity
public class User {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "password")
    private String password;

    public User(int uid, String login, String password) {
        this.uid = uid;
        this.login = login;
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
