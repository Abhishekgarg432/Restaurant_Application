package com.abhishek.restaurantapp.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "restaurant",indices = @Index(value = {"restaurantId"},unique = true))
public class RestaurantModel {
    @PrimaryKey
    @NonNull
    public String restaurantId;

    @SerializedName("name")
    @ColumnInfo(name="name")
    public String name;

    @SerializedName("latlong")
    @ColumnInfo(name="latlong")
    public String latlong;

    @SerializedName("image")
    @ColumnInfo(name="image")
    public String image;

    @SerializedName("contact")
    @ColumnInfo(name="contact")
    public int contact;

    @ColumnInfo(name="rating")
    public float rating;

    @ColumnInfo(name="thumb")
    public boolean thumb;

    public String headerLocation;

    public String address;

    @Override
    public String toString() {
        return "Model{" +
                "restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", venue=" + contact +
                ", headerLocation='" + headerLocation + '\'' +
                '}';
    }
}
