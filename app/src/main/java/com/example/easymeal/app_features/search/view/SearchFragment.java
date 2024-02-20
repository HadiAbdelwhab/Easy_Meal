package com.example.easymeal.app_features.search.view;

import static android.view.View.GONE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.adapters.CategoriesAdapter;
import com.example.easymeal.app_features.home.view.adapters.OnChosenCategoryClickListener;
import com.example.easymeal.app_features.meals.view.adpters.MealsAdapter;
import com.example.easymeal.app_features.meals.view.adpters.OnChosenMealListener;
import com.example.easymeal.app_features.search.presenter.SearchPresenter;
import com.example.easymeal.app_features.search.presenter.SearchPresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.Category;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.Meal;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.util.ConnectivityUtils;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchView, OnChosenMealListener {

    private static final String TAG = "SearchFragment";
    private SearchPresenter presenter;
    private RecyclerView recyclerView;
    private TextInputEditText searchEditText;
    private Chip categoryChip, areaChip, mealChip, ingredientChip;
    private ChipGroup chipGroup;
    private MealsAdapter mealsAdapter;
    private TextView offlinModeTextView;
    private ProgressBar progressBar;

    private final CompositeDisposable disposables = new CompositeDisposable();


    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        if (!ConnectivityUtils.isNetworkAvailable(getApplicationContext())) {
            searchEditText.setVisibility(GONE);
            chipGroup.setVisibility(GONE);
            offlinModeTextView.setVisibility(View.VISIBLE);
        }
        presenter = new SearchPresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                        MealsLocalDataSourceImpl.getInstance(getActivity())));
        setListeners();


    }

    private void setListeners() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                final String[] name = new String[1];
                searchEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        name[0] = s.toString();
                        emitter.onNext(name[0]);

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }
        });
        disposables.add(observable
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(query -> {
                    Log.i(TAG, "On Change Text: " + query);

                    // Add your filtering logic here
                    if (categoryChip.isChecked()) {
                        presenter.getMealsByCategory(query);
                    } else if (areaChip.isChecked()) {
                        presenter.getMealsByArea(query);
                    } else if (mealChip.isChecked()) {
                        presenter.searchMealByName(query);
                    } else if (ingredientChip.isChecked()) {
                        presenter.getMealsByIngredient(query);
                    }
                }, error -> {
                    Log.e(TAG, "Error: " + error.getMessage());
                }));
        chipGroup.setSingleSelection(true);
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                if (areaChip.isChecked()) {
                    categoryChip.setChecked(false);
                    mealChip.setChecked(false);
                    ingredientChip.setChecked(false);
                    progressBar.setVisibility(View.VISIBLE);
                    areaChip.setChipBackgroundColorResource(R.color.primary_color);
                } else {
                    areaChip.setChipBackgroundColorResource(R.color.white);
                }

                if (mealChip.isChecked()) {
                    categoryChip.setChecked(false);
                    areaChip.setChecked(false);
                    ingredientChip.setChecked(false);
                    progressBar.setVisibility(View.VISIBLE);

                    mealChip.setChipBackgroundColorResource(R.color.primary_color);
                } else {
                    mealChip.setChipBackgroundColorResource(R.color.white);
                }

                if (ingredientChip.isChecked()) {
                    categoryChip.setChecked(false);
                    mealChip.setChecked(false);
                    areaChip.setChecked(false);
                    progressBar.setVisibility(View.VISIBLE);

                    ingredientChip.setChipBackgroundColorResource(R.color.primary_color);
                } else {
                    ingredientChip.setChipBackgroundColorResource(R.color.white);
                }

                if (categoryChip.isChecked()) {
                    areaChip.setChecked(false);
                    mealChip.setChecked(false);
                    ingredientChip.setChecked(false);
                    progressBar.setVisibility(View.VISIBLE);

                    categoryChip.setChipBackgroundColorResource(R.color.primary_color);
                } else {
                    categoryChip.setChipBackgroundColorResource(R.color.white);
                }
            }

        });

    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.search_recycler_view);
        searchEditText = view.findViewById(R.id.search_text_field);


        chipGroup = view.findViewById(R.id.chips_group);
        categoryChip = view.findViewById(R.id.chip_category);
        areaChip = view.findViewById(R.id.chip_area);
        mealChip = view.findViewById(R.id.chip_meal);
        ingredientChip = view.findViewById(R.id.chip_ingredient);

        progressBar = view.findViewById(R.id.search_progress_bar);
        offlinModeTextView=view.findViewById(R.id.offline_mode_text_view_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager categoriesLayoutManger = new LinearLayoutManager(getActivity());
        categoriesLayoutManger.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(categoriesLayoutManger);

    }

    private void setAdapter() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }


    @Override
    public void showMealsByCategories(MealsResponse mealsResponse) {
        setAdapter();
        List<Meal> meals = mealsResponse.getMeals();
        Log.i(TAG, "showMealsByIngredients: " + meals);
        mealsAdapter = new MealsAdapter(getActivity(), meals, this);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showMealsByCategoriesErrorMessage(String ErrorMassage) {

    }

    @Override
    public void showMealsByArea(MealsResponse mealsResponse) {
        setAdapter();
        List<Meal> meals = mealsResponse.getMeals();
        Log.i(TAG, "showMealsByIngredients: " + meals);
        mealsAdapter = new MealsAdapter(getActivity(), meals, this);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showMealsByAreaErrorMessage(String errorMessage) {

    }

    @Override
    public void showMealsByIngredients(MealsResponse mealsResponse) {
        setAdapter();
        List<Meal> meals = mealsResponse.getMeals();
        Log.i(TAG, "showMealsByIngredients: " + meals);
        mealsAdapter = new MealsAdapter(getActivity(), meals, this);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showMealsByIngredientsErrorMessage(String errorMessage) {
        Log.i(TAG, "showMealsByIngredientsErrorMessage: " + errorMessage);
    }

    @Override
    public void showMealBySearch(MealsResponse mealsResponse) {
        setAdapter();
        List<Meal> meals = mealsResponse.getMeals();
        Log.i(TAG, "showMealsByIngredients: " + meals);
        mealsAdapter = new MealsAdapter(getActivity(), meals, this);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void showMealBySearchErrorMessage(String errorMessage) {
        Log.i(TAG, "showMealBySearchErrorMessage: " + errorMessage);
    }

    @Override
    public void OnClick(String mealId, View view) {
        SearchFragmentDirections.ActionSearchFragmentToMealDetailsFragment action
            =SearchFragmentDirections.actionSearchFragmentToMealDetailsFragment(mealId);
        Navigation.findNavController(view).navigate(action);

    }
}
