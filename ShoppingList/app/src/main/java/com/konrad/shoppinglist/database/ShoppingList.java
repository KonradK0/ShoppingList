package com.konrad.shoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Konrad on 13.01.2018.
 */

@Entity(foreignKeys = @ForeignKey( entity = User.class, parentColumns = "uid", childColumns = "userUid"))
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private long uid;
    @ColumnInfo(name = "userUid")
    private long userUid;
    @ColumnInfo(name = "name")
    private String name;

    public ShoppingList(long uid, long userUid, String name) {
        this.uid = uid;
        this.userUid = userUid;
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public long getUserUid() {
        return userUid;
    }

    public String getName() {
        return name;
    }
}
