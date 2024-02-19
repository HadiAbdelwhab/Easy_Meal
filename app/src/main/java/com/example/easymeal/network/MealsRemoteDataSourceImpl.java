package com.example.easymeal.network;

import static com.example.easymeal.util.Constants.BASE_URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {

    private static final String TAG = "MealsRemoteDataSourceImpl";
    private static MealsRemoteDataSourceImpl instance;
    private final MealsService service;


    private MealsRemoteDataSourceImpl(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    if (isNetworkAvailable(context)) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build(); // Adjust cache duration as needed
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build(); // Adjust cache duration as needed
                    }
                    return chain.proceed(request);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        service = retrofit.create(MealsService.class);
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static synchronized MealsRemoteDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealsRemoteDataSourceImpl(context);
        }
        return instance;
    }


    @Override
    public void getCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack) {
        Observable<CategoryResponse> observable = service.getAllCategories();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CategoryResponse categoryResponse) {
                        categoriesCallBack.onSuccessResult(categoryResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        categoriesCallBack.onFailure(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack, String mealId) {
        Observable<MealDetailsResponse> observable = service.getMealDetailsById(mealId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealDetailsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MealDetailsResponse mealDetailsResponse) {
                        mealDetailsCallBack.onSuccessMealDetails(mealDetailsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealDetailsCallBack.onFailMealDetails(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack) {
        Observable<AreaListResponse> observable = service.getAllAreas();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AreaListResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull AreaListResponse areaListResponse) {
                        areasCallBack.onSuccessAreaCallBack(areaListResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        areasCallBack.onFailAreaCallBack(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getRandomMeal(NetworkCallBack.RandomMealCallBack randomMealCallBack) {
        Observable<MealDetailsResponse> observable = service.getRandomMeal();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealDetailsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MealDetailsResponse mealDetailsResponse) {
                        randomMealCallBack.onSuccessRandomMeal(mealDetailsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        randomMealCallBack.onFailRandomMeal(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getMealsByCategory(NetworkCallBack.MealsByCategoryCallBack mealsByCategoryCallBack, String categoryName) {
        Observable<MealsResponse> observable = service.getMealsByCategory(categoryName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MealsResponse mealsResponse) {
                        mealsByCategoryCallBack.onSuccessMealsByCategory(mealsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealsByCategoryCallBack.onFailMealsByCategory(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getMealsByArea(NetworkCallBack.MealsByAreaCallBack mealsByAreaCallBack, String areaName) {
        Observable<MealsResponse> observable = service.getMealsByArea(areaName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull MealsResponse mealsResponse) {
                        mealsByAreaCallBack.onSuccessMealsByArea(mealsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealsByAreaCallBack.onFailMealsByArea(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });


    }

    @Override
    public void getIngredients(NetworkCallBack.IngredientsCallBack ingredientsCallBack) {
        Observable<IngredientsResponse> observable = service.getIngredients();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IngredientsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull IngredientsResponse ingredientsResponse) {
                        ingredientsCallBack.onSuccessIngredients(ingredientsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ingredientsCallBack.onFailIngredients(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void searchMealByName(NetworkCallBack.SearchMealCallBack searchMealCallBack,
                                 String mealName) {
        Observable<MealDetailsResponse> observable = service.searchMealByName(mealName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealDetailsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealDetailsResponse mealDetailsResponse) {
                        searchMealCallBack.onSuccessSearchMeal(mealDetailsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchMealCallBack.onFailSearchMeal(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
