package com.logicaltriangle.hnn.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.logicaltriangle.hnn.dao.CategoryDao;
import com.logicaltriangle.hnn.dao.FavoriteDao;
import com.logicaltriangle.hnn.dao.FeaturedItemDao;
import com.logicaltriangle.hnn.dao.ItemDescDao;
import com.logicaltriangle.hnn.entities.Category;
import com.logicaltriangle.hnn.entities.Favorite;
import com.logicaltriangle.hnn.entities.Featured_item;
import com.logicaltriangle.hnn.entities.Item_desc;

@Database(entities = {Category.class, Item_desc.class, Featured_item.class, Favorite.class}, version = 1, exportSchema = false)
public abstract class HnnDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();

    public abstract ItemDescDao itemDescDao();

    public abstract FeaturedItemDao featuredItemDao();

    public abstract FavoriteDao favoriteDao();

}
