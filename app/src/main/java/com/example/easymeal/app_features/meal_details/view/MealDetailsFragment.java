package com.example.easymeal.app_features.meal_details.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenter;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenterImpl;
import com.example.easymeal.R;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private MealDetailsPresenter presenter;
    private static final String TAG = "MealDetailsFragment";
    private ImageView mealImageView;
    private TextView maelNameTextView, instructionsTextView, areaTextView;
    private YouTubePlayerView youTubePlayerView;
    private Button addToFavouriteButton;
    private MealDetailsResponse.MealDetails mealDetails;

    public MealDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        String mealId = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealId();

        presenter = new MealDetailsPresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                        MealsLocalDataSourceImpl.getInstance(getActivity())));
        presenter.getMealDetailsById(mealId);
        setListeners();
    }

    private void initViews(View view) {
        mealImageView = view.findViewById(R.id.meal_image_view);
        maelNameTextView = view.findViewById(R.id.meal_name_text_view);
        instructionsTextView = view.findViewById(R.id.instruction_text_view);
        areaTextView = view.findViewById(R.id.area_text_view);
        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        addToFavouriteButton = view.findViewById(R.id.add_favourite_button);
    }

    private void setListeners() {

        addToFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.insertMeal(mealDetails);
            }
        });
    }

    @Override
    public void showMealDetails(MealDetailsResponse mealDetailsResponse) {
        mealDetails = mealDetailsResponse.getMeals().get(0);
        Glide.with(getActivity())
                .load(mealDetails.getMealThumb())
                .error(R.drawable.laod)
                .placeholder(R.drawable.laod)
                .into(mealImageView);
        maelNameTextView.setText(mealDetails.getMealName());
        instructionsTextView.setText(mealDetails.getInstructions());


    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }


}