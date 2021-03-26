package com.abhishek.restaurantapp.database.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "review")
public class ReviewModel {

    @PrimaryKey(autoGenerate = true)
    public int reviewId;

    public String restaurantId;

    public String comment;
}
