package com.abhishek.restaurantapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.database.model.ReviewModel;
import com.abhishek.restaurantapp.database.repository.ReviewRepository;

import java.util.List;

public class CommentsViewModel extends AndroidViewModel {

    private final ReviewRepository reviewRepository;
    String restaurantId;
    LiveData<List<ReviewModel>> restaurantOnLatLon;

    public CommentsViewModel(@NonNull Application application, String restaurantId) {
        super(application);
        this.restaurantId = restaurantId;
        reviewRepository = new ReviewRepository(application);
        restaurantOnLatLon = reviewRepository.getAllModel(restaurantId);
    }

    public void insert(String review) {
        ReviewModel reviewModel = new ReviewModel();
        reviewModel.comment = review;
        reviewModel.restaurantId = restaurantId;
        reviewRepository.insert(reviewModel);
    }

    public LiveData<List<ReviewModel>> getComments() {
        return restaurantOnLatLon;
    }
}
