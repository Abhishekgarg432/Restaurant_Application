package com.abhishek.restaurantapp.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.database.repository.RestaurantRepository;
import com.abhishek.restaurantapp.pojo.FoursquareResponce;
import com.abhishek.restaurantapp.pojo.Item;
import com.abhishek.restaurantapp.pojo.RestaurantApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantViewModel extends AndroidViewModel implements LocationListener {

    private final RestaurantRepository restaurantRepository;
    String client_id = "BMRA0OSIBFAMQ3BB253NLRK24HMER2PDWMO4BWKKKKJGOD1E";
    String client_secret = "T3K2YNMKHVWGAKGAJNTP44P0STB1IQRON50JDAKYY0ABPYA1";
    MutableLiveData<String> latlon = new MutableLiveData<String>();
    LiveData<List<RestaurantModel>> restaurantOnLatLon;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        restaurantRepository = new RestaurantRepository(application);
        restaurantOnLatLon = Transformations.switchMap(latlon, new Function<String, LiveData<List<RestaurantModel>>>() {
            @Override
            public LiveData<List<RestaurantModel>> apply(String input) {
                return restaurantRepository.getAllModel(input);
            }
        });
    }

    public void update(RestaurantModel restaurantModel) {
        restaurantRepository.update(restaurantModel);
    }

    public LiveData<List<RestaurantModel>> getRestaurantOnLatLon() {
        return restaurantOnLatLon;
    }

    @SuppressLint("MissingPermission")
    public void getLocation() {
        try {
            FusedLocationProviderClient fusedLocationClient = LocationServices.
                    getFusedLocationProviderClient(getApplication());
            fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        String ll = location.getLatitude() + "," + location.getLongitude();
                        getData(ll);
                        latlon.postValue(ll);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getData(final String ll) {
        final RestaurantApi.FourSquareService list = RestaurantApi.getFourSquareService();
        Call<FoursquareResponce> call = list.getForsquareResponse(client_id, client_secret, "20210303", ll, "restaurant");
        call.enqueue(new Callback<FoursquareResponce>() {
            @Override
            public void onResponse(Call<FoursquareResponce> call, Response<FoursquareResponce> response) {
                FoursquareResponce responce = response.body();
                List<Item> item = responce.response.groups.get(0).items;
                final List<RestaurantModel> listModel = new ArrayList<>();
                for (int i = 0; i < item.size(); i++) {
                    RestaurantModel model = new RestaurantModel();
                    model.name = item.get(i).venue.name;
                    model.restaurantId = item.get(i).venue.id;
                    model.address = item.get(i).venue.location.address;
                    model.latlong = ll;
                    listModel.add(model);
                }
                if (response.isSuccessful()) {
                    restaurantRepository.insert(listModel);
                }
            }

            @Override
            public void onFailure(Call<FoursquareResponce> call, Throwable t) {
                Log.d("API", "onERROR" + t.getMessage());
            }
        });

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(this, "Please Wait..." , Toast.LENGTH_SHORT).show();
        double lat = location.getLatitude();
        double lan = location.getLongitude();
        String ll = lat + "," + lan;
        getData(ll);
        latlon.postValue(ll);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
