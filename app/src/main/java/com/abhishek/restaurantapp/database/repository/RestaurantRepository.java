package com.abhishek.restaurantapp.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import com.abhishek.restaurantapp.database.dao.RestaurantDao;
import com.abhishek.restaurantapp.database.RestaurantDataBase;
import com.abhishek.restaurantapp.database.model.RestaurantModel;

public class RestaurantRepository {

    private RestaurantDataBase dataBase;

    public RestaurantRepository(Application application) {
        dataBase = RestaurantDataBase.getINSTANCE(application);
    }

    public void insert(List<RestaurantModel> modelList) {

        new InsertAsynTask(dataBase).execute(modelList);
    }

    public LiveData<List<RestaurantModel>> getAllModel(String latlonString) {
        return dataBase.restaurantDao().getAllModel(latlonString);
    }

    public void update(RestaurantModel restaurantModel) {
        new UpdateAsynTask(dataBase).execute(restaurantModel);
    }

    // to update data in background
    static class UpdateAsynTask extends AsyncTask<RestaurantModel, Void, Void> {

        private RestaurantDao restaurantDao;

        UpdateAsynTask(RestaurantDataBase restaurantDataBase) {
            restaurantDao = restaurantDataBase.restaurantDao();
        }

        @Override
        protected Void doInBackground(RestaurantModel... restaurantModel) {
            //list[0] element
            RestaurantModel restaurantModel1 = restaurantModel[0];
            restaurantDao.update(!restaurantModel1.thumb, restaurantModel1.restaurantId);
            return null;
        }
    }

    // to insert data in background
    static class InsertAsynTask extends AsyncTask<List<RestaurantModel>, Void, Void> {

        private RestaurantDao restaurantDao;

        InsertAsynTask(RestaurantDataBase restaurantDataBase) {

            restaurantDao = restaurantDataBase.restaurantDao();
        }

        @Override
        protected Void doInBackground(List<RestaurantModel>... lists) {
            //list[0] element
            restaurantDao.insert(lists[0]);
            return null;
        }
    }
}
