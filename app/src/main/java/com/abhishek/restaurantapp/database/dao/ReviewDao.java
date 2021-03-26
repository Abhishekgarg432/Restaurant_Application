package com.abhishek.restaurantapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.database.model.ReviewModel;

import java.util.List;

@Dao
public interface ReviewDao {

    @Insert
    void insert(ReviewModel review);
    @Query("SELECT * from review WHERE restaurantId = :restaurantId")
    LiveData<List<ReviewModel>> getAllModel(String restaurantId);

}
