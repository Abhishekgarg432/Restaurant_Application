package com.abhishek.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.abhishek.restaurantapp.database.model.RestaurantModel;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantHolder> {

    private Context context;
    public List<RestaurantModel> models;
    ThumbListener viewModel;

    public RestaurantAdapter(Context context,
                             List<RestaurantModel> models, ThumbListener viewModel) {
        this.context = context;
        this.models = models;
        this.viewModel = viewModel;

    }

    @NonNull
    @Override
    public RestaurantHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.design_item, parent, false);
        return new RestaurantHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantHolder holder, int position) {
        RestaurantModel restaurantModel = models.get(position);
        holder.restaurantName.setText(restaurantModel.name);
        holder.restaurantDiscription.setText(restaurantModel.address);
        holder.rating.setImageResource(restaurantModel.thumb ? R.drawable.ic_baseline_star_rate_24 :
                R.drawable.ic_baseline_star_outline_24);
    }

    public void getAllModels(List<RestaurantModel> models) {
        this.models = models;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class RestaurantHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView restaurantImage;
        TextView restaurantName;
        TextView restaurantDiscription;
        ImageButton rating;

        public RestaurantHolder(@NonNull View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantDiscription = itemView.findViewById(R.id.restaurantDiscription);
            rating = itemView.findViewById(R.id.Rating);
            rating.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.Rating) {
                viewModel.onThumb(models.get(getAdapterPosition()));
            } else {
                viewModel.onComments(models.get(getAdapterPosition()));
            }
        }
    }


    interface ThumbListener {
        void onThumb(RestaurantModel restaurantModel);

        void onComments(RestaurantModel restaurantModel);
    }
}
