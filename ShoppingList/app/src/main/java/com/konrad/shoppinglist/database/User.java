package com.konrad.shoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Konrad on 11.01.2018.
 */

@Entity(indices = @Index(value = "login", unique = true))
public class User {
    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "password")
    private String password;

    public User(long uid, String login, String password) {
        this.uid = uid;
        this.login = login;
        this.password = password;
    }

    public static User empty(){
        return new User(0,"","");
    }

    public boolean isEmpty(){
        return "".equals(this.login) && "".equals(this.password);
    }

    public long getUid() {
        return uid;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
