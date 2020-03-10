package com.logicaltriangle.hnn.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Featured_item {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "cat_id")
    public int catId;

    @NonNull
    @ColumnInfo(name = "item_id")
    public int itemId;
}
