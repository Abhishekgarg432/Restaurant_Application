package com.abhishek.restaurantapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhishek.restaurantapp.database.model.RestaurantModel;
import com.abhishek.restaurantapp.database.model.ReviewModel;
import com.abhishek.restaurantapp.viewmodel.CommentsViewModel;
import com.abhishek.restaurantapp.viewmodel.RestaurantViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class CommentsFragment extends BottomSheetDialogFragment implements CommentsAdapter.CommentListener {
    public static CommentsFragment newInstance(String restaurantId) {
        Bundle bundle = new Bundle();
        bundle.putString("id",restaurantId);
        CommentsFragment commentsFragment = new CommentsFragment();
        commentsFragment.setArguments(bundle);
        return commentsFragment;
    }

    private CommentsViewModel commentsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.CommentsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         commentsViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                ViewModel viewModel = new CommentsViewModel(getActivity().getApplication()
                        , getArguments().getString("id"));
                return (T)viewModel;
            }
        }).get(CommentsViewModel.class);
        final CommentsAdapter commentsAdapter = new CommentsAdapter(getContext(), this);
        recyclerView.setAdapter(commentsAdapter);
        commentsViewModel.getComments().observe(this, new Observer<List<ReviewModel>>() {
            @Override
            public void onChanged(List<ReviewModel> models) {
                commentsAdapter.setComments(models);
            }
        });

    }

    @Override
    public void onComment(String comment) {
        commentsViewModel.insert(comment);
    }
}
