package com.logicaltriangle.hnn.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item_desc {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "cat_id")
    public int catId;

    @NonNull
    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "desc_1")
    public String desc1;

    @ColumnInfo(name = "desc_2")
    public String desc2;

    @ColumnInfo(name = "desc_3")
    public String desc3;

    @ColumnInfo(name = "desc_4")
    public String desc4;

    @ColumnInfo(name = "gif_img_name")
    public String gifImgName;

    @ColumnInfo(name = "img_name_1")
    public String imgName1;

    @ColumnInfo(name = "img_name_2")
    public String imgName2;

    @ColumnInfo(name = "img_name_3")
    public String imgName3;

    @ColumnInfo(name = "img_name_4")
    public String imgName4;

}
