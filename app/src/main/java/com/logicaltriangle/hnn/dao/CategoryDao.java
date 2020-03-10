package com.logicaltriangle.hnn.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import com.logicaltriangle.hnn.entities.Category;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM category")
    List<Category> getAll();
}
