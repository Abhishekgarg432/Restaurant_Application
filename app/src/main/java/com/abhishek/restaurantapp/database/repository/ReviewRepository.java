package com.abhishek.restaurantapp.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.abhishek.restaurantapp.database.RestaurantDataBase;
import com.abhishek.restaurantapp.database.dao.ReviewDao;
import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.database.model.ReviewModel;

import java.util.List;

public class ReviewRepository {

    private RestaurantDataBase dataBase;

    public ReviewRepository(Application application) {
        dataBase = RestaurantDataBase.getINSTANCE(application);
    }


    public LiveData<List<ReviewModel>> getAllModel(String restaurantId) {
        return dataBase.reviewDao().getAllModel(restaurantId);
    }

    public void insert(ReviewModel reviewModel) {
        new InsertAsynTask(dataBase).execute(reviewModel);
    }

    // to insert data in background
    static class InsertAsynTask extends AsyncTask<ReviewModel, Void, Void> {

        private ReviewDao reviewDao;

        InsertAsynTask(RestaurantDataBase restaurantDataBase) {
            reviewDao = restaurantDataBase.reviewDao();
        }

        @Override
        protected Void doInBackground(ReviewModel... lists) {
            //list[0] element
            reviewDao.insert(lists[0]);
            return null;
        }
    }
}
