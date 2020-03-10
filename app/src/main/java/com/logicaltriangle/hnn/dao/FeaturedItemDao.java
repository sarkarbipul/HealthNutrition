package com.logicaltriangle.hnn.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import com.logicaltriangle.hnn.entities.Featured_item;

@Dao
public interface FeaturedItemDao {
    @Query("SELECT * FROM featured_item")
    List<Featured_item> getAllItems();
}
