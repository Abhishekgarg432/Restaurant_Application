package com.abhishek.restaurantapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.abhishek.restaurantapp.database.model.RestaurantModel;

@Dao
public interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RestaurantModel> modelList);

    @Query("SELECT * from restaurant WHERE latlong = :latlong")
    LiveData<List<RestaurantModel>> getAllModel(String latlong);

    @Query("DELETE from restaurant")
    void deleteAll();

    @Query("UPDATE restaurant SET thumb=:thumb WHERE restaurantId = :restaurantId")
    void update(boolean thumb, String restaurantId);

}
