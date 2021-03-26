package com.abhishek.restaurantapp.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.abhishek.restaurantapp.database.dao.RestaurantDao;
import com.abhishek.restaurantapp.database.dao.ReviewDao;
import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.database.model.ReviewModel;

@Database(entities = {RestaurantModel.class, ReviewModel.class}, version = 2)
public abstract class RestaurantDataBase extends RoomDatabase {

    private static final String DATABASE_NAME = "RestaurantDataBase";

    //call function
    public abstract RestaurantDao restaurantDao();

    //call function
    public abstract ReviewDao reviewDao();

    //for single instance
    private static volatile RestaurantDataBase INSTANCE;

    public static RestaurantDataBase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context, RestaurantDataBase.class, DATABASE_NAME)
                            .addCallback(callback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;

    }

    static Callback callback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
