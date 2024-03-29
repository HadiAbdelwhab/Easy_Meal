package com.example.easymeal.app_features.meal_details.view;

import static com.example.easymeal.util.Constants.FAVOURITE_KEY;
import static com.example.easymeal.util.Constants.PLAN_KEY;
import static com.example.easymeal.util.Constants.USER_ID_KEY;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenter;
import com.example.easymeal.app_features.meal_details.presenter.MealDetailsPresenterImpl;
import com.example.easymeal.R;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.SharedPreferencesManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MealDetailsFragment extends Fragment implements MealDetailsView {

    private MealDetailsPresenter presenter;
    private static final String TAG = "MealDetailsFragment";
    private ImageView mealImageView, addToFavouriteImage, addToPlanImage;
    private TextView maelNameTextView, instructionsTextView, areaTextView;
    private YouTubePlayerView youTubePlayerView;
    private MealDetailsResponse.MealDetails mealDetails;
    private RecyclerView ingredientsRecyclerView;
    private IngredientAdapter adapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private SharedPreferencesManager sharedPreferencesManager;

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

        sharedPreferencesManager = new SharedPreferencesManager(requireContext());
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
        ingredientsRecyclerView = view.findViewById(R.id.ingredients_recycler_view);
        addToFavouriteImage = view.findViewById(R.id.add_favourite_image_view);
        addToPlanImage = view.findViewById(R.id.add_plan_image_view);
        getLifecycle().addObserver(youTubePlayerView);

    }

    private void setIngredientsRecyclerView() {
        ingredientsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        ingredientsRecyclerView.setLayoutManager(manager);
    }

    private void setListeners() {
        addToFavouriteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.isLoggedIn()) {
                    saveMeal(mealDetails, FAVOURITE_KEY);
                } else {
                    Toast.makeText(getActivity(), "You are in guest mode can not add to favourite", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addToPlanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.isLoggedIn()) {
                    showDatePickerDialog();
                } else {
                    Toast.makeText(getActivity(), "You are in guest mode can not add to plan", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void saveMeal(MealDetailsResponse.MealDetails mealDetails, String databaseKey) {
        DatabaseReference reference = database.getReference(databaseKey);

        if (mealDetails == null) {
            Log.e(TAG, "mealDetails object is null, cannot save to Firebase");
            return;
        }
        mealDetails.setDatabaseKey(databaseKey);
        mealDetails.setPlanDate("0");
        presenter.insertMeal(mealDetails);
        Map<String, Object> mealDetailsMap = new HashMap<>();
        mealDetailsMap.put("userId", getActivity().getIntent().getStringExtra(USER_ID_KEY));
        mealDetailsMap.put("mealId", mealDetails.getIdMeal());
        mealDetailsMap.put("mealName", mealDetails.getMealName());
        mealDetailsMap.put("mealImage", mealDetails.getMealThumb());
        mealDetailsMap.put("databaseKey", mealDetails.getDatabaseKey());
        mealDetailsMap.put("date", mealDetails.getPlanDate());

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

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        long minDateMillis = System.currentTimeMillis();
        long maxDateMillis = minDateMillis + (7 * 24 * 60 * 60 * 1000);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {

                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        saveMealWithDate(mealDetails, PLAN_KEY, selectedDate);
                    }
                },
                year,
                month,
                dayOfMonth
        );

        datePickerDialog.getDatePicker().setMinDate(minDateMillis);
        datePickerDialog.getDatePicker().setMaxDate(maxDateMillis);

        datePickerDialog.show();
    }


    private void saveMealWithDate(MealDetailsResponse.MealDetails mealDetails, String planKey, String selectedDate) {

        DatabaseReference reference = database.getReference(planKey);

        if (mealDetails == null) {
            Log.e(TAG, "mealDetails object is null, cannot save to Firebase");
            return;
        }
        if (selectedDate == null) {

            return;
        }
        mealDetails.setDatabaseKey(planKey);
        mealDetails.setPlanDate(selectedDate);
        presenter.insertMeal(mealDetails);
        Map<String, Object> mealDetailsMap = new HashMap<>();
        mealDetailsMap.put("userId", getActivity().getIntent().getStringExtra(USER_ID_KEY));
        mealDetailsMap.put("mealId", mealDetails.getIdMeal());
        mealDetailsMap.put("mealName", mealDetails.getMealName());
        mealDetailsMap.put("mealImage", mealDetails.getMealThumb());
        mealDetailsMap.put("databaseKey", mealDetails.getDatabaseKey());
        mealDetailsMap.put("instructions", mealDetails.getInstructions());
        mealDetailsMap.put("date", mealDetails.getPlanDate());

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

    @Override
    public void showMealDetails(MealDetailsResponse mealDetailsResponse) {
        mealDetails = mealDetailsResponse.getMeals().get(0);
        Glide.with(getActivity())
                .load(mealDetails.getMealThumb())
                .error(R.drawable.laod)
                .placeholder(R.drawable.laod)
                .into(mealImageView);
        maelNameTextView.setText(mealDetails.getMealName());
        //areaTextView.setText("Area :"+mealDetails.getArea());
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
        if (!TextUtils.isEmpty(mealDetails.getYoutubeURL())) {
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(getVideoIdFromUrl(mealDetails.getYoutubeURL()), 0);
                }
            });
        } else {

            youTubePlayerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showIngredients(IngredientsResponse ingredientsResponse) {

    }

    @Override
    public void showIngredientsErrorMessage(String errorMessage) {

    }
    private String getVideoIdFromUrl(String youtubeUrl) {
        // Extract video ID from the URL
        String videoId = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0) {
            String pattern = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed'\\/|watch\\?v=|%2Fvideos%2F|embed'\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|%2Fv%2F|youtu.be\\/|embed\\/|watch\\?v=|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|%2Fv%2F|[\\?\\&]v=|\\/videos\\/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed'\\/|watch\\?v=|%2Fvideos%2F|embed'\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|%2Fv%2F)([\\w\\-_]*)";
            Pattern compiledPattern = Pattern.compile(pattern);
            Matcher matcher = compiledPattern.matcher(youtubeUrl);
            if (matcher.find()) {
                videoId = matcher.group();
            }
        }
        return videoId;
    }

}