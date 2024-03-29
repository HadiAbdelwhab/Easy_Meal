package com.example.easymeal.app_features.home.view;

import static android.view.View.GONE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.easymeal.app_features.MainActivity;
import com.example.easymeal.app_features.home.presenter.HomePresenter;
import com.example.easymeal.app_features.home.presenter.HomePresenterImpl;
import com.example.easymeal.app_features.home.view.adapters.CategoriesAdapter;
import com.example.easymeal.app_features.home.view.adapters.CountriesAdapter;
import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.adapters.OnChosenAreaListener;
import com.example.easymeal.app_features.home.view.adapters.OnChosenCategoryClickListener;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.Category;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.ConnectivityUtils;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements HomeView, OnChosenCategoryClickListener, OnChosenAreaListener {

    private CountriesAdapter countriesAdapter;
    private CategoriesAdapter categoriesAdapter;
    private RecyclerView countriesRecyclerView, categoriesRecyclerView;
    private static final String TAG = "HomeFragment";
    private List<Category> categories;
    private HomePresenter presenter;
    private ImageView randomMealImage;
    private TextView mealNameTextView, offlineModeTextView;
    private ConstraintLayout homeConstraintLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        if (!ConnectivityUtils.isNetworkAvailable(getApplicationContext())) {
            homeConstraintLayout.setVisibility(GONE);
            offlineModeTextView.setVisibility(View.VISIBLE);
        }
        setUI();
        presenter = new HomePresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                        MealsLocalDataSourceImpl.getInstance(getActivity())));
        presenter.getAllCategories();
        presenter.getAllAreas();
        presenter.getRandomMeal();

    }

    private void initViews(View view) {
        countriesRecyclerView = view.findViewById(R.id.countries_recycler_view);
        categoriesRecyclerView = view.findViewById(R.id.categories_recycler_view);
        randomMealImage = view.findViewById(R.id.random_image_view_home);
        mealNameTextView = view.findViewById(R.id.meal_name_text_view_home);
        offlineModeTextView=view.findViewById(R.id.offline_mode_text_view_home);
        homeConstraintLayout=view.findViewById(R.id.home_constrain_layout);
    }

    private void setUI() {
        countriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager countriesLayoutManager = new LinearLayoutManager(getActivity());
        countriesLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        countriesRecyclerView.setLayoutManager(countriesLayoutManager);

        categoriesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager categoriesLayoutManger = new LinearLayoutManager(getActivity());
        categoriesLayoutManger.setOrientation(RecyclerView.HORIZONTAL);
        categoriesRecyclerView.setLayoutManager(categoriesLayoutManger);


    }

    @Override
    public void showCategories(CategoryResponse categoryResponse) {
        categories = categoryResponse.getCategories();
        //Log.i(TAG, "showCategories: "+categories);
        categoriesAdapter = new CategoriesAdapter(getActivity(), categories, this);
        categoriesRecyclerView.setAdapter(categoriesAdapter);
    }

    @Override
    public void showCategoriesErrorMessage(String errorMassage) {
        //Log.i(TAG, "showErrorMessage: "+errorMassage);
    }

    @Override
    public void showAreas(AreaListResponse areaListResponse) {


        List<AreaListResponse.Area> populatedAreas = new ArrayList<>();
        populatedAreas.add(new AreaListResponse.Area("American", R.drawable.american));
        populatedAreas.add(new AreaListResponse.Area("British", R.drawable.british));
        populatedAreas.add(new AreaListResponse.Area("Canadian", R.drawable.canadian));
        populatedAreas.add(new AreaListResponse.Area("Chinese", R.drawable.chinese));
        populatedAreas.add(new AreaListResponse.Area("Croatian", R.drawable.croatian));
        populatedAreas.add(new AreaListResponse.Area("Dutch", R.drawable.dutch));
        populatedAreas.add(new AreaListResponse.Area("Egyptian", R.drawable.egyptian));
        populatedAreas.add(new AreaListResponse.Area("Filipino", R.drawable.filipino));
        populatedAreas.add(new AreaListResponse.Area("French", R.drawable.french));
        populatedAreas.add(new AreaListResponse.Area("Greek", R.drawable.greek));
        populatedAreas.add(new AreaListResponse.Area("Indian", R.drawable.indian));
        populatedAreas.add(new AreaListResponse.Area("Irish", R.drawable.irish));
        populatedAreas.add(new AreaListResponse.Area("Italian", R.drawable.italian));
        populatedAreas.add(new AreaListResponse.Area("Jamaican", R.drawable.jamaican));
        populatedAreas.add(new AreaListResponse.Area("Japanese", R.drawable.japanese));
        populatedAreas.add(new AreaListResponse.Area("Kenyan", R.drawable.kenyan));
        populatedAreas.add(new AreaListResponse.Area("Malaysian", R.drawable.malaysian));
        populatedAreas.add(new AreaListResponse.Area("Mexican", R.drawable.mexican));
        populatedAreas.add(new AreaListResponse.Area("Moroccan", R.drawable.moroccan));
        populatedAreas.add(new AreaListResponse.Area("Polish", R.drawable.polish));
        populatedAreas.add(new AreaListResponse.Area("Portuguese", R.drawable.portuguese));
        populatedAreas.add(new AreaListResponse.Area("Russian", R.drawable.russian));
        populatedAreas.add(new AreaListResponse.Area("Spanish", R.drawable.spanish));
        populatedAreas.add(new AreaListResponse.Area("Thai", R.drawable.thai));
        populatedAreas.add(new AreaListResponse.Area("Tunisian", R.drawable.tunisian));
        populatedAreas.add(new AreaListResponse.Area("Turkish", R.drawable.turkish));
        populatedAreas.add(new AreaListResponse.Area("Unknown", R.drawable.unknown));
        populatedAreas.add(new AreaListResponse.Area("Vietnamese", R.drawable.vietnamese));
        countriesAdapter = new CountriesAdapter(getActivity(), populatedAreas, this);
        countriesRecyclerView.setAdapter(countriesAdapter);

    }

    @Override
    public void showAreasErrorMessage(String errorMessage) {
        //Log.i(TAG, "showAreasErrorMessage: " + errorMessage);
    }

    @Override
    public void showRandomMeal(MealDetailsResponse mealDetails) {
        MealDetailsResponse.MealDetails randomMeal = mealDetails.getMeals().get(0);
        Glide.with(requireContext())
                .load(randomMeal.getMealThumb())
                .error(R.drawable.laod)
                .into(randomMealImage);
        mealNameTextView.setText(randomMeal.getMealName());
        Log.i(TAG, "showRandomMeal: " + randomMeal.toString());
        randomMealImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragmentDirections.ActionHomeFragmentToMealDetailsFragment action =
                        HomeFragmentDirections.actionHomeFragmentToMealDetailsFragment(randomMeal.getIdMeal());
                action.setMealId(randomMeal.getIdMeal());
                Navigation.findNavController(v).navigate(action);

            }
        });
    }

    @Override
    public void showRandomMealErrorMessage(String errorMessage) {

        Log.i(TAG, "showRandomMealErrorMessage: " + errorMessage);
    }

    @Override
    public void showIngredients(IngredientsResponse ingredientsResponse) {

    }

    @Override
    public void showIngredientsErrorMessage(String errorMessage) {

    }


    @Override
    public void onCategoryClick(String categoryName, View view) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment toMealsFragment =
                HomeFragmentDirections.actionHomeFragmentToMealsFragment(categoryName, null);
        Navigation.findNavController(view).navigate(toMealsFragment);
    }

    @Override
    public void onAreaClick(String countryName, View view) {
        HomeFragmentDirections.ActionHomeFragmentToMealsFragment toMealsFragment =
                HomeFragmentDirections.actionHomeFragmentToMealsFragment(null, countryName);
        Navigation.findNavController(view).navigate(toMealsFragment);
    }
}