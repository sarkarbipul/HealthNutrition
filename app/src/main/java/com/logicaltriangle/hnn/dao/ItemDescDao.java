package com.logicaltriangle.hnn.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import com.logicaltriangle.hnn.entities.Item_desc;

@Dao
public interface ItemDescDao {
    @Query("SELECT * FROM item_desc")
    List<Item_desc> getAll();

    @Query("SELECT * FROM item_desc WHERE cat_id=:catid")
    List<Item_desc> getItemsByCatId(int catid);

    @Query("SELECT * FROM item_desc WHERE id=:itemid LIMIT 1")
    Item_desc getItemDesc(int itemid);

    @Query("SELECT * FROM item_desc WHERE title LIKE :query")
    List<Item_desc> getSearchResults(String query);
}
