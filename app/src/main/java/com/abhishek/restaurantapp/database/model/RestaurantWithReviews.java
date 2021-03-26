package com.abhishek.restaurantapp.database.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RestaurantWithReviews {

    @Embedded
    public RestaurantModel restaurantModel;
    @Relation(
            parentColumn = "restaurantId",
            entityColumn = "restaurantId"
    )
    public List<ReviewModel> playlists;
}
