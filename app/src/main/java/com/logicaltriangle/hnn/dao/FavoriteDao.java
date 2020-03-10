package com.logicaltriangle.hnn.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.logicaltriangle.hnn.entities.Favorite;
import com.logicaltriangle.hnn.entities.Item_desc;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    public List<Favorite> getAllFavItems();

    @Insert
    public void insert(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE item_id=:itemID LIMIT 1")
    Favorite getFavItemId(int itemID);

    @Query("Delete FROM favorite WHERE id=:id")
    public void delete(int id);

}
