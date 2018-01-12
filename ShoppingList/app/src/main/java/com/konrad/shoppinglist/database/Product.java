package com.konrad.shoppinglist.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Konrad on 12.01.2018.
 */

@Entity(foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = {"uid"}, childColumns = {"categoryUid"}, onDelete = ForeignKey.CASCADE)} )
public class Product {

    @PrimaryKey(autoGenerate = true)
    private long uid;
    @ColumnInfo(name = "categoryUid")
    private long categoryUid;
    @ColumnInfo(name = "name")
    private String name;

    public Product(long uid, long categoryUid, String name) {
        this.uid = uid;
        this.categoryUid = categoryUid;
        this.name = name;
    }

    public long getUid() {
        return uid;
    }

    public long getCategoryUid() {
        return categoryUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
