package com.abhishek.restaurantapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.viewmodel.RestaurantViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantAdapter.ThumbListener {
    ImageButton button;
    //RecyclerView
    RecyclerView recyclerView;
    //ViewModel
    private RestaurantViewModel restaurantViewModel;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.GPS);
        //recyclerview
        recyclerView = findViewById(R.id.RestaurantList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        List<RestaurantModel> modelList = new ArrayList<>();
        restaurantAdapter = new RestaurantAdapter(this, modelList, this);
        //viewModel
        restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        restaurantViewModel.getRestaurantOnLatLon().observe(this, new Observer<List<RestaurantModel>>() {
            @Override
            public void onChanged(List<RestaurantModel> models) {
                restaurantAdapter.getAllModels(models);
                recyclerView.setAdapter(restaurantAdapter);
                //Toast.makeText(MainActivity.this, "Updating...", Toast.LENGTH_SHORT).show();
            }
        });
        //Runtime permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        } else {
            restaurantViewModel.getLocation();
        }

        //Fetch Live Location
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Fetching...", Toast.LENGTH_LONG).show();
                restaurantViewModel.getLocation();
            }
        });
    }

    @Override
    public void onThumb(RestaurantModel restaurantModel) {
        restaurantViewModel.update(restaurantModel);
    }

    @Override
    public void onComments(RestaurantModel restaurantModel) {
        CommentsFragment addPhotoBottomDialogFragment =
                CommentsFragment.newInstance(restaurantModel.restaurantId);
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(),
                "comment");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    restaurantViewModel.getLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}







