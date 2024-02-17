package com.example.easymeal.app_features.search.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.easymeal.R;
import com.example.easymeal.app_features.home.view.adapters.CategoriesAdapter;
import com.example.easymeal.app_features.meals.view.adpters.MealsAdapter;
import com.example.easymeal.app_features.search.presenter.SearchPresenter;
import com.example.easymeal.app_features.search.presenter.SearchPresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.Category;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.Meal;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;
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
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchFragment extends Fragment implements SearchView {

    private static final String TAG = "SearchFragment";
    private CategoriesAdapter adapter;
    private SearchPresenter presenter;
    private RecyclerView recyclerView;
    private TextInputEditText searchEditText;
    private Chip categoryChip;
    private List<String> categoriesName;
    private List<String> filteredNames;
    private List<Category> categoryList;
    private ChipGroup chipGroup;
    private MealsAdapter mealsAdapter;
    private CardView searchCardView;
    private TextView searhMealTextView;
    private ImageView searchMealImageView;

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
        presenter = new SearchPresenterImpl(this,
                MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                        MealsLocalDataSourceImpl.getInstance(getActivity())));
        setListeners();
        adapter = new CategoriesAdapter(requireActivity(), categoriesName); // Assuming you have a constructor for CategoriesAdapter
        recyclerView.setAdapter(adapter);

    }

    private void setListeners() {

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                presenter.searchMealByName(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        disposables.add(RxTextView.textChanges(searchEditText)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(text -> {
                    Log.i(TAG, "On Change Text: " + text);
                }, error -> {
                    Log.e(TAG, "Error: ");
                }));
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.search_recycler_view);
        categoryChip = view.findViewById(R.id.chip_category);
        searchEditText = view.findViewById(R.id.search_text_field);
        chipGroup = view.findViewById(R.id.chips_group);
        searchCardView = view.findViewById(R.id.search_meal_card);
        searhMealTextView = view.findViewById(R.id.search_meal_text_view_meals);
        searchMealImageView = view.findViewById(R.id.search_meal_image_view_meals);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager categoriesLayoutManger = new LinearLayoutManager(getActivity());
        categoriesLayoutManger.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(categoriesLayoutManger);

    }

    private void setAdapter() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
    }

    private void filterCategories(String query) {
        Observable.create(emitter -> {
                    if (categoryList != null) {
                        List<String> categoryNames = new ArrayList<>();
                        for (Category category : categoryList) {
                            categoryNames.add(category.getCategoryName()); // Assuming 'getStrCategory()' is the method to retrieve category name
                        }

                        List<String> filteredList = categoryNames.stream()
                                .filter(name -> name.toLowerCase().contains(query.toLowerCase()))
                                .collect(Collectors.toList());

                        emitter.onNext(filteredList);
                        emitter.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filteredList -> {
                            if (filteredList instanceof List) {
                                filteredNames.clear();
                                filteredNames.addAll((List<String>) filteredList);
                                adapter.notifyDataSetChanged();
                            }
                        },
                        throwable -> {
                            Log.i(TAG, "filterCategories: error");
                        }
                );
    }

    @Override
    public void showCategories(CategoryResponse categoryResponse) {
        categoryList = categoryResponse.getCategories();
        categoriesName = categoryList.stream().map(Category::getCategoryName).collect(Collectors.toList());
        filterCategories("Beef"); // To initially load all categories
    }

    @Override
    public void showCategoriesErrorMessage(String ErrorMassage) {

    }

    @Override
    public void showIngredients(IngredientsResponse ingredientsResponse) {

    }

    @Override
    public void showIngredientsErrorMessage(String ErrorMassage) {

    }

    @Override
    public void showAreas(AreaListResponse areaListResponse) {

    }

    @Override
    public void showAreasErrorMessage(String ErrorMassage) {

    }

    @Override
    public void searchMealByNameResult(MealDetailsResponse mealDetailsResponse) {

        recyclerView.setVisibility(View.GONE);
        searchCardView.setVisibility(View.VISIBLE);

        searhMealTextView.setText(mealDetailsResponse.getMeals().get(0).getMealName());
        Glide.with(getActivity()).load(mealDetailsResponse.getMeals().get(0).getMealThumb())
                .placeholder(R.drawable.laod)
                .error(R.drawable.laod)
                .into(searchMealImageView);



    }

    @Override
    public void searchMealByNameErrorMessage(String errorMessage) {

    }


}