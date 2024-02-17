package com.example.easymeal.app_features.meal_details.view;

import static com.example.easymeal.util.Constants.USER_ID_KEY;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private MealDetailsPresenter presenter;
    private static final String TAG = "MealDetailsFragment";
    private ImageView mealImageView;
    private TextView maelNameTextView, instructionsTextView, areaTextView;
    private YouTubePlayerView youTubePlayerView;
    private Button addToFavouriteButton;
    private MealDetailsResponse.MealDetails mealDetails;
    private RecyclerView ingredientsRecyclerView;
    private IngredientAdapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference("favourites");


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
        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
    }

    private void setIngredientsRecyclerView() {
        ingredientsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientsRecyclerView.setLayoutManager(manager);
    }

    private void setListeners() {
        addToFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mealDetails == null) {
                    Log.e(TAG, "mealDetails object is null, cannot save to Firebase");
                    return;
                }
                presenter.insertMeal(mealDetails);
                Map<String, Object> mealDetailsMap = new HashMap<>();
                mealDetailsMap.put("userId",getActivity().getIntent().getStringExtra(USER_ID_KEY));
                mealDetailsMap.put("mealId", mealDetails.getIdMeal());
                mealDetailsMap.put("mealName", mealDetails.getMealName());
                mealDetailsMap.put("mealImage",mealDetails.getMealThumb());

                String key = reference.push().getKey();
                reference.child(key).setValue(mealDetailsMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Meal details saved to Firebase successfully!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "Error saving meal details to Firebase:", e);
                            }
                        });
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
        List<String> ingredients = new ArrayList<>();

        ingredients.add(mealDetails.getIngredient1());
        ingredients.add(mealDetails.getIngredient2());
        ingredients.add(mealDetails.getIngredient3());
        ingredients.add(mealDetails.getIngredient4());
        ingredients.add(mealDetails.getIngredient5());
        Log.i(TAG, "showMealDetails: " + ingredients);
        setIngredientsRecyclerView();
        adapter = new IngredientAdapter(ingredients, getActivity());
        ingredientsRecyclerView.setAdapter(adapter);

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showIngredients(IngredientsResponse ingredientsResponse) {
        /*setIngredientsRecyclerView();
        adapter=new IngredientAdapter(ingredientsResponse.getIngredientsList(),getActivity());
        ingredientsRecyclerView.setAdapter(adapter);*/
    }

    @Override
    public void showIngredientsErrorMessage(String errorMessage) {

    }


}