package com.example.easymeal.app_features.plan.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.easymeal.R;
import com.example.easymeal.app_features.plan.presenter.PlanPresenter;
import com.example.easymeal.app_features.plan.presenter.PlanPresenterImpl;
import com.example.easymeal.database.MealsLocalDataSourceImpl;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepositoryImpl;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;

import java.util.Calendar;
import java.util.List;


public class PlanFragment extends Fragment implements PlanView {


    private static final String TAG = "PlanFragment";
    private PlanPresenter presenter;
    private CardView planCardView;
    private Button chosePlanMealButton;
    private TextView mealNameTextView;
    private ImageView mealImageView;

    public PlanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setListeners();
        presenter = new PlanPresenterImpl(this, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getActivity()),
                MealsLocalDataSourceImpl.getInstance(getActivity())));
        //presenter.getPlanMeals("2024-2-18");
    }

    private void initViews(View view) {
        chosePlanMealButton = view.findViewById(R.id.show_meal_plan_button);
        planCardView = view.findViewById(R.id.plan_card);
        mealNameTextView = view.findViewById(R.id.meal_name_plan);
        mealImageView=view.findViewById(R.id.meal_image_view_plan);
    }

    @Override
    public void showPlanMeals(List<MealDetailsResponse.MealDetails> mealDetails) {
        Log.i(TAG, "showPlanMeals: " + mealDetails);
        planCardView.setVisibility(View.VISIBLE);
        mealNameTextView.setText(mealDetails.get(0).getMealName());
        Glide.with(getActivity())
                .load(mealDetails.get(0).getMealThumb())
                .error(R.drawable.laod)
                .placeholder(R.drawable.laod)
                .into(mealImageView);


    }

    @Override
    public void showPlanMealsErrorMessage(String errorMessage) {
        Log.i(TAG, "showPlanMealsErrorMessage: ");
    }

    private void setListeners() {
        chosePlanMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDayOfMonth;
                        presenter.getPlanMeals(selectedDate);
                    }
                },
                year,
                month,
                dayOfMonth
        );

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

}